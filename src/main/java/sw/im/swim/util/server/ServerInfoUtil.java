package sw.im.swim.util.server;


import com.google.gson.*;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import sw.im.swim.worker.noti.NotiWorker;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class ServerInfoUtil {

    private static final String[] arr = {"KB", "MB", "GB", "TB"};


    public static final PublicIpInfo GET_PUBLIC_IP() {

        String IP_GET_URL = "http://ipinfo.io";

        PublicIpInfo publicIpInfo = new PublicIpInfo();

        String[] arr = {
                "ip", "city", "region", "country", "loc", "org", "timezone", "postal"
        };

        CloseableHttpResponse response = null;
        try (CloseableHttpClient httpClient = HttpClients.createDefault();) {

            HttpPost httpPost = new HttpPost(IP_GET_URL);

            response = httpClient.execute(httpPost);

            ResponseHandler<String> handler = new BasicResponseHandler();
            String body = handler.handleResponse(response);

            log.debug("ip body == " + body);

            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();

            publicIpInfo.setIp(jsonObject.get("ip").getAsString());
            publicIpInfo.setCity(jsonObject.get("city").getAsString());
            publicIpInfo.setRegion(jsonObject.get("region").getAsString());
            publicIpInfo.setCountry(jsonObject.get("country").getAsString());
            publicIpInfo.setLoc(jsonObject.get("loc").getAsString());
            publicIpInfo.setOrg(jsonObject.get("org").getAsString());
            publicIpInfo.setTimezone(jsonObject.get("timezone").getAsString());
            publicIpInfo.setPostal(jsonObject.get("postal").getAsString());

        } catch (Exception e) {
            log.error("----", e);
        } finally {
            try {
                response.close();
            } catch (Exception e) {
            }
        }
        return publicIpInfo;

    }


    public static ServerInfo getServerInfo() {
        ServerInfo serverInfo = new ServerInfo();

        // 네트워크 정보 get
        try {
            serverInfo = getServerInfoFromIfconfig();
        } catch (Exception e) {
        }

        // CPU 정보 get
        List<CpuInfo> cpuList = new ArrayList<>();
        String line = "";
        CpuInfo cpuInfo = null;

//        Set<String> cpuPhysicalIdSet = new HashSet<>();

        HashMap<String, Integer> cpuCntMap = new HashMap<>();

        try {
            FileInputStream fis = new FileInputStream("/proc/cpuinfo");
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));

            try {
                String physicalId = "";

                while ((line = br.readLine()) != null) {

                    if (cpuInfo == null) {
                        cpuInfo = new CpuInfo();
                    }

                    if (line.startsWith("model name")) {
                        cpuInfo.setName(line.split(":")[1].trim());
                    }
                    if (line.startsWith("cpu MHz")) {
                        cpuInfo.setClock(line.split(":")[1].trim() + " MHz");
                    }
                    if (line.startsWith("cache size")) {
                        cpuInfo.setCache(line.split(":")[1].trim());
                    }
                    if (line.startsWith("physical id")) {
                        physicalId = line.split(":")[1].trim();
                    }
                    if (line.startsWith("cpu cores")) {
                        try {
                            String b = line.split(":")[1].trim();
                            int a = Integer.parseInt(b);
                            cpuInfo.setCore(a);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    if (cpuInfo != null) {
                        if (cpuInfo.getCache() != null && cpuInfo.getClock() != null && cpuInfo.getName() != null) {
                            cpuList.add(cpuInfo);

                            boolean alreadyFound = cpuCntMap.keySet().contains(cpuInfo.getName());

                            if (alreadyFound) {
                                cpuCntMap.put(cpuInfo.getName(), cpuCntMap.get(cpuInfo.getName()) + 1);
                            } else {
                                cpuCntMap.put(cpuInfo.getName(), 1);
                            }

                            cpuInfo = null;
                        }
                    }

                } // while end

                HashMap<String, CpuInfo> cpuSet = new HashMap<>();

                for (int i = 0; i < cpuList.size(); i++) {
                    CpuInfo cpu = cpuList.get(i);

                    try {
                        int times = cpuCntMap.get(cpu.getName());
                        cpu.setThread(times * cpu.getCore());

                        cpuSet.put(cpu.getName(), cpu);

                    } catch (Exception e) {
                    }

                }

                List<CpuInfo> result = new ArrayList<>();

                cpuSet.forEach((k, v) -> {
                    result.add(v);
                });

                serverInfo.setCpuList(result);

            } catch (Exception e) {
            }

        } catch (Exception e) {
        }

        // mem info
        line = "";
        try {
            FileInputStream fis = new FileInputStream("/proc/meminfo");
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));

            try {
                while ((line = br.readLine()) != null) {

                    if (line.startsWith("MemTotal")) {
                        String mem = line.split(":")[1].trim();
                        serverInfo.setTotalMem(getMemNumeric(mem));
                    } else if (line.startsWith("MemFree")) {
                        String mem = line.split(":")[1].trim();
                        serverInfo.setFreeMem(getMemNumeric(mem));
                    } else if (line.startsWith("MemAvailable")) {
                        String mem = line.split(":")[1].trim();
                        serverInfo.setAvailableMem(getMemNumeric(mem));
                    } else if (line.startsWith("SwapCached")) {
                        String mem = line.split(":")[1].trim();
                        serverInfo.setSwapMem(getMemNumeric(mem));
                    }

                }
            } catch (Exception e) {
            }

            serverInfo.setAvailableMemStr(format(serverInfo.getAvailableMem()));
            serverInfo.setFreeMemStr(format(serverInfo.getFreeMem()));
            serverInfo.setSwapMemStr(format(serverInfo.getSwapMem()));
            serverInfo.setTotalMemStr(format(serverInfo.getTotalMem()));

        } catch (Exception e) {
        }

        // OS info
        try {
            FileInputStream fis = new FileInputStream("/etc/issue");
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));

            String output = "";

            try {
                while ((line = br.readLine()) != null) {
                    output += line;
                }
            } catch (Exception e) {
            }

            output = output.replace("\n", "");
            output = output.replace("\\n", "");
            output = output.replace("\\l", "");
            output = output.trim();

            serverInfo.setOs(output);

        } catch (Exception e) {
        }

        // kernel info
        try {
            FileInputStream fis = new FileInputStream("/proc/version");
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));

            String output = "";

            try {
                while ((line = br.readLine()) != null) {
                    output += line;
                }
            } catch (Exception e) {
            }

            output = output.replace("\n", "");
            output = output.replace("\\l", "");
            output = output.trim();

            serverInfo.setKernel(output);

        } catch (Exception e) {
        }

        return serverInfo;
    }

    private static Long getMemNumeric(String memString) {
        Long result = 0L;
        // log.debug(" getMemNumeric \t input \t>>> " + memString);
        try {
            memString = memString.toUpperCase();
            for (int i = 0; i < arr.length; i++) {
                String temp = arr[i];
                if (memString.contains(temp)) {
                    memString = memString.replaceAll(temp, "").trim();
                    result = Long.parseLong(memString);
                    result = result * 1000 ^ (i + 1);
                }
            }
        } catch (Exception e) {
        }
        // log.debug(" getMemNumeric \t output \t>>> " + result);
        return result;
    }

    public static String format(Long mem) {
        String result = "";
        String byteString = "";
        float byteValue = 0;
        try {

            Long[] arr1024 = new Long[4];
            Long[] arr1000 = new Long[4];

            for (int i = 0; i < arr1024.length; i++) {
                arr1024[i] = (long) Math.pow(1024, (i + 1));
            }
            for (int i = 0; i < arr1000.length; i++) {
                arr1000[i] = (long) Math.pow(1000, (i + 1));
            }

            for (int i = arr1000.length - 1; i >= 0; i--) {
                if (mem > arr1000[i]) {
                    mem = (mem * 1000) / arr1000[i];
                    byteValue = ((float) mem) / 1000;
                    byteString = String.format("%5.3f", byteValue);
                    byteString += " ";
                    byteString += arr[i];
                    break;
                }
            }
            result = byteString;

        } catch (Exception e) {
        }
        return result;
    }

    private static ServerInfo getServerInfoFromIfconfig() {

        ServerInfo serverInfo = new ServerInfo();
        BufferedReader in = null;
        Pattern ipPattern = null;
        final String ipRegexString = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
        try {
            ipPattern = Pattern.compile(ipRegexString);
        } catch (Exception e) {
        }

        try {
            ProcessBuilder ps = new ProcessBuilder("sh", "-c", "ip r | grep default");
            ps.redirectErrorStream(true);

            Process pr = ps.start();

            in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                log.debug(line);

                Matcher matcher = ipPattern.matcher(line);

                if (matcher.find()) {
                    String gateway = line.substring(matcher.start(), matcher.end());
                    serverInfo.setGateway(gateway);
                    log.debug("gateway => " + gateway);
                }

            }
            pr.waitFor();
        } catch (Exception e) {
        } finally {
            try {
                in.close();
            } catch (Exception e) {
            }
        }

        // BufferedReader in = null;
        ArrayList<String> list = new ArrayList<>();
        try {
            ProcessBuilder ps = new ProcessBuilder("ifconfig");
            ps.redirectErrorStream(true);

            Process pr = ps.start();

            in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                log.debug(line);

                String line_ = line.replace("\t", " ").replace("  ", " ").trim();
                list.add(line_);
            }
            list.add("");
            list.add("");
            pr.waitFor();
        } catch (Exception e) {
        } finally {
            try {
                in.close();
            } catch (Exception e) {
            }
        }
        list.add("end");
        list.add("");

        // log.debug("\n\n\n\n\n");

        for (int i = 0; i < list.size(); i++) {

            String line = list.get(i);

            if (line.equals("end")) {
                break;
            }

            // log.debug("[ " + i + "] \t" + line);

            boolean isOtherInterface = (line.contains("lo:") || line.contains("docker"));
            boolean isInterfaceLine = (line.contains(" mtu ") && line.contains("flags"));
            boolean isInterfaceNameNotTooLong = (line.indexOf("flags") < 10);

            // log.debug("line.indexOf flag \t" + line.indexOf("flags") + "\t" +
            // isInterfaceNameNotTooLong);

            if (isOtherInterface && isInterfaceLine && isInterfaceNameNotTooLong) {
                i += 7;
                continue;
            }

            if (isInterfaceLine && isInterfaceNameNotTooLong) {

                try {
                    String nextline = list.get(i + 1);
                    int IDX_inet = nextline.indexOf("inet ");
                    int IDX_netmask = nextline.indexOf(" netmask ");

                    String ipStr = nextline.substring(IDX_inet, IDX_netmask);
                    String netmaskStr = nextline.substring(IDX_netmask);

                    log.debug("ipStr      \t -> " + ipStr);
                    log.debug("netmaskStr \t -> " + netmaskStr);

                    Matcher ipMatcher = ipPattern.matcher(ipStr);
                    Matcher netmaskMatcher = ipPattern.matcher(netmaskStr);

                    boolean foundIp = ipMatcher.find();
                    boolean foundNetmask = netmaskMatcher.find();

                    log.debug("\t foundIp  => " + foundIp + "\t foundNetmask  => " + foundNetmask);

                    ipStr = ipStr.substring(ipMatcher.start(), ipMatcher.end());
                    netmaskStr = netmaskStr.substring(netmaskMatcher.start(), netmaskMatcher.end());

                    log.debug("\t ipStr  => " + ipStr + "\t netmaskStr  => " + netmaskStr);

                    serverInfo.setIpAddress(ipStr);
                    serverInfo.setSubnetMask(netmaskStr);

                    break;

                } catch (Exception e) {
                }

            }

        } // for each end

        return serverInfo;
    } // end getServerInfoFromIfconfig()

    @Getter
    @Setter
    public static class DiskInfo {

        private String fileSystem;
        private long size;
        private long used;
        private long avail;
        private String mountedOn;

        public String getSizeH() {
            return format(size);
        }

        public String getUsedH() {
            return format(used);
        }

        public String getAvailH() {
            return format(avail);
        }

        public double getUsableRatio() {
            try {
                return ((double) used) / ((double) size);
            } catch (Exception e) {
                return 0.0;
            }
        }

        public String getUsablePercentage() {
            return String.format("%5.2f", getUsableRatio());
        }

    }

    @Getter
    @Setter
    public static class CpuInfo {

        // cpu
        private String name;
        private String clock;
        private String cache;
        private int core = 1;
        private int thread = 1;

    }

    @Getter
    @Setter
    public static class PublicIpInfo {

        // cpu
        private String ip;
        private String city;
        private String region;
        private String country;
        private String loc;
        private String org;
        private String timezone;
        private String postal;

    }

    @Getter
    @Setter
    public static class ServerInfo {

        // network
        private String ipAddress;
        private String subnetMask;
        private String gateway;

        // os
        private String mac;
        private String os;
        private String kernel;

        // mem
        private Long totalMem;
        private Long availableMem;
        private Long swapMem;
        private Long freeMem;
        // mem
        private String totalMemStr;
        private String availableMemStr;
        private String swapMemStr;
        private String freeMemStr;

        // cpu
        private List<CpuInfo> cpuList;

        private List<DiskInfo> diskList;
    }

    public static void main(String[] args) {

        ServerInfo info = ServerInfoUtil.getServerInfoFromIfconfig();

        // log.debug();
        // log.debug();
        // log.debug();
        // log.debug(info);
        // log.debug();
        // log.debug();
        // log.debug(ServerInfoUtil.format(info.getTotalMem()));
        // log.debug();
        // log.debug();

    }

}
