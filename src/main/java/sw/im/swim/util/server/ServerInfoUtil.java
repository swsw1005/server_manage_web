package sw.im.swim.util.server;


import com.google.gson.Gson;
import sw.im.swim.worker.noti.NotiWorker;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServerInfoUtil {

    private static final String[] arr = {"KB", "MB", "GB", "TB"};

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
                        if (cpuInfo.getCache() != null && cpuInfo.getClock() != null && cpuInfo.getCore() != null && cpuInfo.getName() != null) {
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
        // System.out.println(" getMemNumeric \t input \t>>> " + memString);
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
        // System.out.println(" getMemNumeric \t output \t>>> " + result);
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
                System.out.println(line);

                Matcher matcher = ipPattern.matcher(line);

                if (matcher.find()) {
                    String gateway = line.substring(matcher.start(), matcher.end());
                    serverInfo.setGateway(gateway);
                    System.out.println("gateway => " + gateway);
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
                System.out.println(line);

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

        // System.out.println("\n\n\n\n\n");

        for (int i = 0; i < list.size(); i++) {

            String line = list.get(i);

            if (line.equals("end")) {
                break;
            }

            // System.out.println("[ " + i + "] \t" + line);

            boolean isOtherInterface = (line.contains("lo:") || line.contains("docker"));
            boolean isInterfaceLine = (line.contains(" mtu ") && line.contains("flags"));
            boolean isInterfaceNameNotTooLong = (line.indexOf("flags") < 10);

            // System.out.println("line.indexOf flag \t" + line.indexOf("flags") + "\t" +
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

                    System.out.println("ipStr      \t -> " + ipStr);
                    System.out.println("netmaskStr \t -> " + netmaskStr);

                    Matcher ipMatcher = ipPattern.matcher(ipStr);
                    Matcher netmaskMatcher = ipPattern.matcher(netmaskStr);

                    boolean foundIp = ipMatcher.find();
                    boolean foundNetmask = netmaskMatcher.find();

                    System.out.println("\t foundIp  => " + foundIp + "\t foundNetmask  => " + foundNetmask);

                    ipStr = ipStr.substring(ipMatcher.start(), ipMatcher.end());
                    netmaskStr = netmaskStr.substring(netmaskMatcher.start(), netmaskMatcher.end());

                    System.out.println("\t ipStr  => " + ipStr + "\t netmaskStr  => " + netmaskStr);

                    serverInfo.setIpAddress(ipStr);
                    serverInfo.setSubnetMask(netmaskStr);

                    break;

                } catch (Exception e) {
                }

            }

        } // for each end

        return serverInfo;
    } // end getServerInfoFromIfconfig()

    public static class CpuInfo {

        // cpu
        private String name;
        private String clock;
        private String cache;
        private int core = 1;
        private int thread = 1;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getClock() {
            return clock;
        }

        public void setClock(String clock) {
            this.clock = clock;
        }

        public String getCache() {
            return cache;
        }

        public void setCache(String cache) {
            this.cache = cache;
        }

        public Integer getCore() {
            return core;
        }

        public void setCore(Integer core) {
            this.core = core;
        }

        public Integer getThread() {
            return thread;
        }

        public void setThread(Integer thread) {
            this.thread = thread;
        }
    }

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

        /////////////////////////////////////////////////////////////////////////

        public String getIpAddress() {
            return ipAddress;
        }

        public void setIpAddress(String ipAddress) {
            this.ipAddress = ipAddress;
        }

        public String getSubnetMask() {
            return subnetMask;
        }

        public void setSubnetMask(String subnetMask) {
            this.subnetMask = subnetMask;
        }

        public String getGateway() {
            return gateway;
        }

        public void setGateway(String gateway) {
            this.gateway = gateway;
        }

        public String getMac() {
            return mac;
        }

        public void setMac(String mac) {
            this.mac = mac;
        }

        public String getOs() {
            return os;
        }

        public void setOs(String os) {
            this.os = os;
        }

        public String getKernel() {
            return kernel;
        }

        public void setKernel(String kernel) {
            this.kernel = kernel;
        }

        public Long getTotalMem() {
            return totalMem;
        }

        public void setTotalMem(Long totalMem) {
            this.totalMem = totalMem;
        }

        public Long getAvailableMem() {
            return availableMem;
        }

        public void setAvailableMem(Long availableMem) {
            this.availableMem = availableMem;
        }

        public Long getSwapMem() {
            return swapMem;
        }

        public void setSwapMem(Long swapMem) {
            this.swapMem = swapMem;
        }

        public Long getFreeMem() {
            return freeMem;
        }

        public void setFreeMem(Long freeMem) {
            this.freeMem = freeMem;
        }

        public String getTotalMemStr() {
            return totalMemStr;
        }

        public void setTotalMemStr(String totalMemStr) {
            this.totalMemStr = totalMemStr;
        }

        public String getAvailableMemStr() {
            return availableMemStr;
        }

        public void setAvailableMemStr(String availableMemStr) {
            this.availableMemStr = availableMemStr;
        }

        public String getSwapMemStr() {
            return swapMemStr;
        }

        public void setSwapMemStr(String swapMemStr) {
            this.swapMemStr = swapMemStr;
        }

        public String getFreeMemStr() {
            return freeMemStr;
        }

        public void setFreeMemStr(String freeMemStr) {
            this.freeMemStr = freeMemStr;
        }

        public List<CpuInfo> getCpuList() {
            return cpuList;
        }

        public void setCpuList(List<CpuInfo> cpuList) {
            this.cpuList = cpuList;
        }
    }

    public static void main(String[] args) {

        ServerInfo info = ServerInfoUtil.getServerInfoFromIfconfig();

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println(info);
        System.out.println();
        System.out.println();
        System.out.println(ServerInfoUtil.format(info.getTotalMem()));
        System.out.println();
        System.out.println();

    }

}
