package sw.im.swim.service;

import com.google.gson.Gson;
import kr.swim.util.process.ProcessExecutor;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sw.im.swim.bean.dto.SpeedTestResultDto;
import sw.im.swim.bean.dto.SpeedTestServerEntityDto;
import sw.im.swim.bean.entity.SpeedTestResultEntity;
import sw.im.swim.bean.entity.SpeedTestServerEntity;
import sw.im.swim.bean.util.SpeedtestServerLIstParser;
import sw.im.swim.repository.SpeedTestResultEntityRepository;
import sw.im.swim.repository.SpeedTestServerEntityRepository;
import sw.im.swim.service.querydsl.SpeedTestQueryDsl;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SpeedTestService {

    private final SpeedTestResultEntityRepository speedTestResultEntityRepository;

    private final SpeedTestServerEntityRepository speedTestServerEntityRepository;

    private final SpeedTestQueryDsl speedTestQueryDsl;

    private final ModelMapper modelMapper;

    private static final String[] arr = {"sh", "-c", "speedtest-cli --json"};

    public void saveServer(String line) {
        /**
         * TODO 자동 speedtest 서버 등록
         * speedtest-cli --list
         *
         */
        /**
         * 형식
         *  6527) kdatacenter.com (Seoul, South Korea) [2.21 km]
         * 37235) Unicom (Shenyang, China) [532.27 km]
         * 27277) DOM.RU (Vladivostok, Russia) [745.98 km]
         * 33284) Beeline (Vladivostok, Russia) [745.98 km]
         *  6375) Vladivostok State University of Economics (Vladivostok, Russia) [745.98 km]
         * 34115) China Telecom TianJin-5G (TianJin, China) [865.99 km]
         * 25637) Chinamobile-5G (Shanghai, China) [868.86 km]
         *  5317) 江苏电信5G (Yangzhou, China) [897.80 km]
         * 26352) China Telecom JiangSu 5G (Nanjing, China) [968.62 km]
         * 26404) 安徽移动5G (Hefei, China) [1089.01 km]
         */

        try {

            String[] arr = SpeedtestServerLIstParser.parse(line);

            SpeedTestServerEntity entity = SpeedTestServerEntity.builder()
                    .serverId(Integer.parseInt(arr[0]))
                    .name(arr[1])
                    .city(arr[2])
                    .country(arr[3])
                    .build();

            speedTestServerEntityRepository.save(entity);
        } catch (Exception e) {
            log.warn(e + " | " + line + " | " + e.getMessage());
        }


    }

    /**
     * <PRE>
     *
     * </PRE>
     *
     * @return
     * @throws NoSuchAlgorithmException
     */
    public List<SpeedTestServerEntityDto> getServerList() throws NoSuchAlgorithmException {

        List<SpeedTestServerEntity> list_ = speedTestServerEntityRepository.findAll();

        List<SpeedTestServerEntityDto> list = new ArrayList<>();

        for (int i = 0; i < list_.size(); i++) {
            SpeedTestServerEntity temp_ = list_.get(i);

            if (temp_.getCountry().toLowerCase().contains("korea") || temp_.getCountry().equalsIgnoreCase("KR")) {
                list.add(modelMapper.map(temp_, SpeedTestServerEntityDto.class));
                continue;
            }

            final int random = Integer.parseInt(String.valueOf(SecureRandom.getInstanceStrong().nextInt()).substring(1, 2));

            final boolean add = (random > 7);

            log.debug("not KR .... random ? " + random + "\t" + add);

            if (add) {
                list.add(modelMapper.map(temp_, SpeedTestServerEntityDto.class));
            }

        }
        return list;
    }

    public void speedTest(Long speedTestServerSid, int speedTestServerId) throws Exception {
        try {

            final String[] arr = {"sh", "-c", "speedtest-cli --server=" + speedTestServerId + " --json"};

            log.debug("speedtest command  " + new Gson().toJson(arr));

            String speedResult = ProcessExecutor.runSimpleCommand(arr);

            log.info("speedResult   " + speedResult);

            insertSpeedTest(speedResult, speedTestServerSid);

        } catch (IllegalStateException e) {
            throw new IllegalStateException(e.getMessage() + "____");
        } catch (NullPointerException e) {
            throw new NullPointerException(e.getMessage() + "____");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new Exception(e.getMessage() + "____");
        }
    }


    public void insertSpeedTest(String jsonStr, Long speedTestServerSid) throws IllegalStateException, NullPointerException, Exception {

        try {
            JsonObject jsonObject = JsonParser.parseString(jsonStr).getAsJsonObject();
            JsonObject serverJsonObject = jsonObject.get("server").getAsJsonObject();
            JsonObject clientJsonObject = jsonObject.get("client").getAsJsonObject();

            double downloadSpeed = jsonObject.get("download").getAsDouble();
            double uploadSpeed = jsonObject.get("upload").getAsDouble();
            double ping = jsonObject.get("ping").getAsDouble();

            Optional<SpeedTestServerEntity> aaa = speedTestServerEntityRepository.findById(speedTestServerSid);
            SpeedTestServerEntity speedTestServerEntity = aaa.get();

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

            if (speedTestServerEntity.getServerId() == server_id) {

                speedTestServerEntity.setUrl(server_url);
                speedTestServerEntity.setHost(server_host);
                speedTestServerEntity.setLatitude(server_lat);
                speedTestServerEntity.setLongitude(server_lon);
                speedTestServerEntity.setSponsor(server_sponsor);

                speedTestServerEntityRepository.save(speedTestServerEntity);
            }

            String client_ip = clientJsonObject.get("ip").getAsString();
            String client_country = clientJsonObject.get("country").getAsString();
            double client_lat = clientJsonObject.get("lat").getAsDouble();
            double client_lon = clientJsonObject.get("lon").getAsDouble();
            String client_isp = clientJsonObject.get("isp").getAsString();

            SpeedTestResultEntity speedTestResultEntity = SpeedTestResultEntity.builder()
                    .download(downloadSpeed)
                    .upload(uploadSpeed)
                    .ping(ping)
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

//            List<SpeedTestResultEntity> list = speedTestQueryDsl.getListByLimitAndSearch(dto, pageable);
            Sort sort = Sort.by("created_at");
            List<SpeedTestResultEntity> list = speedTestResultEntityRepository.findAll(sort);

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


//    public List<String> getHostList() {
//        return speedTestQueryDsl.getHostList();
//    }

//    public List<String> getCountryList() {
//        return speedTestQueryDsl.getCountryList();
//    }

//    public List<String> getNameList() {
//        return speedTestQueryDsl.getNameList();
//    }


}
