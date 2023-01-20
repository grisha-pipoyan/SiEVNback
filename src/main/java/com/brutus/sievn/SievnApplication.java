package com.brutus.sievn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
public class SievnApplication {

    public static void main(String[] args) {
        SpringApplication.run(SievnApplication.class, args);
    }

//    @Bean
//    CommandLineRunner init(AppUserRepo appUserRepo,
//                           HouseInfoRepo houseInfoRepo) {
//        return (args) -> {
//
//            Optional<AppUser> byEmail = appUserRepo.findByEmail("grisha@mail.ru");
//            AppUser appUser = byEmail.get();
//
//            List<HouseInfo> allData = houseInfoRepo.findAll();
//
//            List<HouseInfo> newHouseInfos = List.copyOf(allData);
//
//            allData.forEach(houseInfo -> {
//                if (houseInfo.getAppUser() == null) {
//                    houseInfoRepo.delete(houseInfo);
//                }
//            });
//
//            newHouseInfos.forEach(
//                    houseInfo -> {
//                        houseInfo.setAppUser(appUser);
//                    }
//            );
//
//            houseInfoRepo.saveAll(newHouseInfos);
//
//        };
//    }

}
