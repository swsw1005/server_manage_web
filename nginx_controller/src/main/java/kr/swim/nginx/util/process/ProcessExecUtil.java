package kr.swim.nginx.util.process;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ProcessExecUtil {


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
        } catch (IOException e) {
        } catch (Exception e) {
            log.error(e.getClass().getSimpleName());
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


    public static final String RUN_READ_COMMAND(final String[] commandArr) {
        String cli = "";
        Process process = null;
        BufferedReader br = null;
        InputStreamReader inputStreamReader = null;
        try {
            process = Runtime.getRuntime().exec(commandArr);
            inputStreamReader = new InputStreamReader(process.getInputStream());
            br = new BufferedReader(inputStreamReader);
            String line = null;
            while ((line = br.readLine()) != null) {
                cli += "\n";
                cli += line;
            }
        } catch (IOException e) {
        } catch (Exception e) {
            log.error(e.getMessage());
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
        return cli;
    }


    public static final String RUN_READ_COMMAND(final String command) {
        String cli = "";
        Process process = null;
        BufferedReader br = null;
        InputStreamReader inputStreamReader = null;
        try {
            process = Runtime.getRuntime().exec(command);
            inputStreamReader = new InputStreamReader(process.getInputStream());
            br = new BufferedReader(inputStreamReader);
            String line = null;
            while ((line = br.readLine()) != null) {
                cli += "\n";
                cli += line;
            }
        } catch (IOException e) {
        } catch (Exception e) {
            log.error(e.getMessage(), e);
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
        return cli;
    }


}
