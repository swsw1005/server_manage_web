package sw.im.swim.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sw.im.swim.bean.dto.SpeedTestClientDto;
import sw.im.swim.bean.dto.SpeedTestResultDto;
import sw.im.swim.bean.dto.SpeedTestServerDto;
import sw.im.swim.bean.entity.SpeedTestClientEntity;
import sw.im.swim.bean.entity.SpeedTestResultEntity;
import sw.im.swim.bean.entity.SpeedTestServerEntity;
import sw.im.swim.repository.SpeedTestClientEntityRepository;
import sw.im.swim.repository.SpeedTestResultEntityRepository;
import sw.im.swim.repository.SpeedTestServerEntityRepository;
import sw.im.swim.service.querydsl.SpeedTestQueryDsl;
import sw.im.swim.util.process.ProcessExecUtil;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SpeedTestService {

    private final SpeedTestClientEntityRepository speedTestClientEntityRepository;
    private final SpeedTestServerEntityRepository speedTestServerEntityRepository;
    private final SpeedTestResultEntityRepository speedTestResultEntityRepository;

    private final SpeedTestQueryDsl speedTestQueryDsl;

    private final ModelMapper modelMapper;

    private static final String[] arr = {"sh", "-c", "speedtest-cli --json"};

    public void speedTest() throws Exception {
        try {
            String speedResult = ProcessExecUtil.RUN_READ_COMMAND(arr);

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

            SpeedTestServerEntity server = getSpeedTestServerEntityFromJson(serverJsonObject);
            SpeedTestClientEntity client = getSpeedTestClientEntityFromJson(clientJsonObject);

            SpeedTestResultEntity speedTestResultEntity = SpeedTestResultEntity.builder()
                    .download(downloadSpeed)
                    .upload(uploadSpeed)
                    .ping(ping)
                    .speedTestClientEntity(client)
                    .speedTestServerEntity(server)
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


    private SpeedTestServerEntity getSpeedTestServerEntityFromJson(JsonObject jsonObject) {

        String url = jsonObject.get("url").getAsString();
        String name = jsonObject.get("name").getAsString();
        String country = jsonObject.get("country").getAsString();
        String cc = jsonObject.get("cc").getAsString();
        String sponsor = jsonObject.get("sponsor").getAsString();
        String host = jsonObject.get("host").getAsString();
        double lat = jsonObject.get("lat").getAsDouble();
        double lon = jsonObject.get("lon").getAsDouble();
        double latency = jsonObject.get("latency").getAsDouble();
        int id = jsonObject.get("id").getAsInt();

        SpeedTestServerEntity entity = SpeedTestServerEntity.builder()
                .url(url)
                .name(name)
                .country(country)
                .cc(cc)
                .sponsor(sponsor)
                .host(host)
                .latency(latency)
                .latitude(lat)
                .longitude(lon)
                .id((long) id)
                .build();

        return speedTestServerEntityRepository.save(entity);
    }

    private SpeedTestClientEntity getSpeedTestClientEntityFromJson(JsonObject jsonObject) {

        String ip = jsonObject.get("ip").getAsString();
        String country = jsonObject.get("country").getAsString();
        double lat = jsonObject.get("lat").getAsDouble();
        double lon = jsonObject.get("lon").getAsDouble();
        String isp = jsonObject.get("isp").getAsString();

        SpeedTestClientEntity entity = SpeedTestClientEntity.builder()
                .country(country)
                .latitude(lat)
                .longitude(lon)
                .ip(ip)
                .isp(isp)
                .build();
        return speedTestClientEntityRepository.save(entity);
    }


    public List<SpeedTestResultDto> getList(int pageNum, int pageSize) {

        List<SpeedTestResultDto> resultList = new ArrayList<>();
        try {

            Pageable pageable = PageRequest.of(pageNum, pageSize);

            List<SpeedTestResultEntity> list = speedTestQueryDsl.getListByLimitAndSearch(null, pageable);

            for (int i = 0; i < list.size(); i++) {

                SpeedTestResultEntity speedTestResult = list.get(i);
                SpeedTestClientEntity speedTestClient = speedTestResult.getSpeedTestClientEntity();
                SpeedTestServerEntity speedTestServer = speedTestResult.getSpeedTestServerEntity();

                SpeedTestResultDto var1 = modelMapper.map(speedTestResult, SpeedTestResultDto.class);
                SpeedTestClientDto var2 = modelMapper.map(speedTestClient, SpeedTestClientDto.class);
                SpeedTestServerDto var3 = modelMapper.map(speedTestServer, SpeedTestServerDto.class);

                var1.setSpeedTestClientDto(var2);
                var1.setSpeedTestServerDto(var3);

                resultList.add(var1);
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return resultList;
    }

}
