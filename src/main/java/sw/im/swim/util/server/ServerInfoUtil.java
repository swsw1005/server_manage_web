package sw.im.swim.util.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServerInfoUtil {

    // String ipRegexString = "\\d[1-3]{.}[1]\\d[1-3]{.}[1]\\d[1-3]{.}[1]\\d[1-3]";
    private static final String ipRegexString = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";

    private static final String[] arr = {"KB", "MB", "GB", "TB"};

    private static final String[] IFCONFIG_CMD = {"sh", "-c", "ifconfig"};
    private static final String[] HOSTNAME_CMD = {"sh", "-c", "hostname -I"};

    public static ServerInfo getServerInfo() {
        ServerInfo serverInfo = new ServerInfo();

        Pattern ipPattern = Pattern.compile(ipRegexString);

        // 네트워크 정보 get
        try {

            List<String> IP_ = RUN_READ_COMMAND_LIST(HOSTNAME_CMD);

            String IP = IP_.get(0).trim();

            // System.out.println("--------------" + IP);

            Matcher hostMatcher = ipPattern.matcher(IP);

            if (hostMatcher.matches()) {
                serverInfo.setIpAddress(IP);
            }

            IFCONFIG_CMD[2] = IFCONFIG_CMD[2] + " | grep " + IP;

            List<String> ifconfigList_ = RUN_READ_COMMAND_LIST(IFCONFIG_CMD);

            List<String> ifconfigList = new ArrayList<>();

            for (int i = 0; i < ifconfigList_.size(); i++) {
                String temp__ = ifconfigList_.get(i)
                        .replaceAll("\t", "   ")
                        .replace("  ", " ")
                        .trim();
                ifconfigList.add(temp__);
            }
            for (int i = 0; i < ifconfigList.size(); i++) {
                String line = ifconfigList.get(i);

                // System.out.println();
                // System.out.println(line);

                if (line.length() < 10) {
                    continue;
                }

                boolean startsWithInet = line.startsWith("inet ");
                String[] lineArr = line.split(" ");
                boolean arrLong4 = lineArr.length >= 4;

                // System.out.println("\t startsWithInet = " + startsWithInet + "\t arrLong4 = "
                // + arrLong4);

                if (startsWithInet && arrLong4) {
                } else {
                    continue;
                }

                Matcher ipMatcher = ipPattern.matcher(lineArr[1]);
                Matcher netmaskMatcher = ipPattern.matcher(lineArr[3]);

                boolean isIpFound = ipMatcher.find();
                boolean isNetmaskFound = netmaskMatcher.find();
                boolean isIpMatch = lineArr[1].equals(IP);

                // System.out.println("lineArr[1] " + lineArr[1]);
                // System.out.println("IP " + IP);
                // System.out.println("\t isIpFound = " + isIpFound + "\t isNetmaskFound = " +
                // isNetmaskFound
                // + "\t isIpMatch = " + isIpMatch);

                if (isIpFound && isNetmaskFound && isIpMatch) {
                    serverInfo.setSubnetMask(lineArr[3].trim());
                    break;
                } else {
                    continue;
                }

            } // for end

            int idx = IP.lastIndexOf(".");

            String gateway = IP.substring(0, idx) + ".1";

            // System.out.println("gateway ====" + gateway + "====");

            serverInfo.setGateway(gateway);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
            } catch (Exception e) {
            }
        }

        // CPU 정보 get
        List<CpuInfo> cpuList = new ArrayList<>();
        String line = "";
        CpuInfo cpuInfo = null;

        Set<String> cpuPhysicalIdSet = new HashSet<>();

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
                    if (line.startsWith("siblings")) {
                        try {
                            String b = line.split(":")[1].trim();
                            int a = Integer.parseInt(b);
                            cpuInfo.setCore(a);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (line.startsWith("cpu cores")) {
                        try {
                            cpuInfo.setCores(Integer.parseInt(line.split(":")[1].trim()));
                        } catch (Exception e) {
                        }
                    }

                    if (cpuInfo != null) {
                        if (!cpuPhysicalIdSet.contains(physicalId)) {
                            if (cpuInfo.getCache() != null && cpuInfo.getClock() != null && cpuInfo.getCore() != null
                                    && cpuInfo.getName() != null && cpuInfo.getCores() != null) {
                                cpuList.add(cpuInfo);
                                cpuPhysicalIdSet.add(physicalId);

                                cpuInfo = null;
                            }
                        }
                    }

                } // while end
                serverInfo.setCpuList(cpuList);

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

    public static class CpuInfo {

        // cpu
        private String name;
        private String clock;
        private String cache;
        private Integer core;
        private Integer cores;

        // private Integer thread;
        public Integer getThread() {
            return core * cores;
        }

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

        public Integer getCores() {
            return cores;
        }

        public void setCores(Integer cores) {
            this.cores = cores;
        }

        @Override
        public String toString() {
            return "CpuInfo [cache=" + cache + ", clock=" + clock + ", core=" + core + ", cores=" + cores + ", name="
                    + name + "]";
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

        @Override
        public String toString() {

            String cpuStr = "";

            for (int i = 0; i < cpuList.size(); i++) {
                try {
                    String cpu_ = cpuList.get(i).toString();
                    cpuStr += "\n\t\t" + cpu_;
                } catch (Exception e) {
                }

            }

            return "\n\n ServerInfo ["
                    + "\n\t availableMem=" + availableMem
                    + "\n\t availableMemStr=" + availableMemStr
                    + "\n\t cpuList=" + cpuStr
                    + "\n\t freeMem=" + freeMem
                    + "\n\t freeMemStr=" + freeMemStr
                    + "\n\t gateway=" + gateway
                    + "\n\t ipAddress=" + ipAddress
                    + "\n\t kernel=" + kernel
                    + "\n\t mac=" + mac
                    + "\n\t os=" + os
                    + "\n\t subnetMask=" + subnetMask
                    + "\n\t swapMem=" + swapMem
                    + "\n\t swapMemStr=" + swapMemStr
                    + "\n\t totalMem=" + totalMem
                    + "\n\t totalMemStr=" + totalMemStr
                    + "\n\n";
        }
    }

    public static final List<String> RUN_READ_COMMAND_LIST(final String[] commandArr) {
        String cli = "";
        Process process = null;
        BufferedReader br = null;
        InputStreamReader inputStreamReader = null;
        List<String> list = new ArrayList<>();
        try {
            process = Runtime.getRuntime().exec(commandArr);
            inputStreamReader = new InputStreamReader(process.getInputStream());
            br = new BufferedReader(inputStreamReader);
            String line = null;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        } catch (Exception e) {
        } finally {
            try {
                process.destroy();
            } catch (Exception e) {
            }
            try {
                br.close();
            } catch (Exception e) {
            }
            try {
                inputStreamReader.close();
            } catch (Exception e) {
            }
        }
        return list;
    }

    public static String NETMASK(final String input) {
        String tempSubnetMask = "";
        switch (input) {
            case "24":
                tempSubnetMask = "255.255.255.0";
                break;
            case "25":
                tempSubnetMask = "255.255.255.128";
                break;
            case "26":
                tempSubnetMask = "255.255.255.192";
                break;
            case "27":
                tempSubnetMask = "255.255.255.224";
                break;
            case "28":
                tempSubnetMask = "255.255.255.240";
                break;
            case "29":
                tempSubnetMask = "255.255.255.248";
                break;
            case "30":
                tempSubnetMask = "255.255.255.252";
                break;
            default:
                break;
        }
        return tempSubnetMask;
    }

    public static void main(String[] args) {

        ServerInfo info = ServerInfoUtil.getServerInfo();

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
