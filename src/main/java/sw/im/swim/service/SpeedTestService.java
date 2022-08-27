package sw.im.swim.service;

import com.caffeine.lib.process.ProcessExecutor;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sw.im.swim.bean.dto.SpeedTestResultDto;
import sw.im.swim.bean.entity.SpeedTestResultEntity;
import sw.im.swim.repository.SpeedTestResultEntityRepository;
import sw.im.swim.service.querydsl.SpeedTestQueryDsl;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SpeedTestService {

    private final SpeedTestResultEntityRepository speedTestResultEntityRepository;

    private final SpeedTestQueryDsl speedTestQueryDsl;

    private final ModelMapper modelMapper;

    private static final String[] arr = {"sh", "-c", "speedtest-cli --json"};

    public void speedTest() throws Exception {
        try {
            String speedResult = ProcessExecutor.runSimpleCommand(arr);

            log.info("speedResult   " + speedResult);

            insertSpeedTest(speedResult);

        } catch (IllegalStateException e) {
            throw new IllegalStateException(e.getMessage() + "____");
        } catch (NullPointerException e) {
            throw new NullPointerException(e.getMessage() + "____");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new Exception(e.getMessage() + "____");
        }
    }

    public void insertSpeedTest(String jsonStr) throws IllegalStateException, NullPointerException, Exception {

        try {
            JsonObject jsonObject = JsonParser.parseString(jsonStr).getAsJsonObject();
            JsonObject serverJsonObject = jsonObject.get("server").getAsJsonObject();
            JsonObject clientJsonObject = jsonObject.get("client").getAsJsonObject();

            double downloadSpeed = jsonObject.get("download").getAsDouble();
            double uploadSpeed = jsonObject.get("upload").getAsDouble();
            double ping = jsonObject.get("ping").getAsDouble();


            String server_url = serverJsonObject.get("url").getAsString();
            String server_name = serverJsonObject.get("name").getAsString();
            String server_country = serverJsonObject.get("country").getAsString();
            String server_cc = serverJsonObject.get("cc").getAsString();
            String server_sponsor = serverJsonObject.get("sponsor").getAsString();
            String server_host = serverJsonObject.get("host").getAsString();
            double server_lat = serverJsonObject.get("lat").getAsDouble();
            double server_lon = serverJsonObject.get("lon").getAsDouble();
            double server_latency = serverJsonObject.get("latency").getAsDouble();
            int server_id = serverJsonObject.get("id").getAsInt();

            String client_ip = clientJsonObject.get("ip").getAsString();
            String client_country = clientJsonObject.get("country").getAsString();
            double client_lat = clientJsonObject.get("lat").getAsDouble();
            double client_lon = clientJsonObject.get("lon").getAsDouble();
            String client_isp = clientJsonObject.get("isp").getAsString();

            SpeedTestResultEntity speedTestResultEntity = SpeedTestResultEntity.builder()
                    .download(downloadSpeed)
                    .upload(uploadSpeed)
                    .ping(ping)
                    .server_url(server_url)
                    .server_name(server_name)
                    .server_country(server_country)
                    .server_sponsor(server_sponsor)
                    .server_host(server_host)
                    .server_latency(server_latency)
                    .server_latitude(server_lat)
                    .server_longitude(server_lon)
                    .client_ip(client_ip)
                    .client_country(client_country)
                    .client_latitude(client_lat)
                    .client_longitude(client_lon)
                    .client_isp(client_isp)
                    .build();

            speedTestResultEntityRepository.save(speedTestResultEntity);

        } catch (IllegalStateException e) {
            throw new IllegalStateException(e.getMessage() + "____");
        } catch (NullPointerException e) {
            throw new NullPointerException(e.getMessage() + "____");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new Exception(e.getMessage() + "____");
        }
    }


    public List<SpeedTestResultDto> getList(SpeedTestResultDto dto, int pageNum, int pageSize) {

        List<SpeedTestResultDto> resultList = new ArrayList<>();
        try {

            Pageable pageable = PageRequest.of(pageNum, pageSize);

            List<SpeedTestResultEntity> list = speedTestQueryDsl.getListByLimitAndSearch(dto, pageable);

            for (int i = 0; i < list.size(); i++) {

                SpeedTestResultEntity speedTestResult = list.get(i);

                SpeedTestResultDto var1 = modelMapper.map(speedTestResult, SpeedTestResultDto.class);

                resultList.add(var1);
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return resultList;
    }


    public List<String> getHostList() {
        return speedTestQueryDsl.getHostList();
    }

    public List<String> getCountryList() {
        return speedTestQueryDsl.getCountryList();
    }

    public List<String> getNameList() {
        return speedTestQueryDsl.getNameList();
    }


}
