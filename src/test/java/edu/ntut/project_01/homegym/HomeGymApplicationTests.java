package edu.ntut.project_01.homegym;

import edu.ntut.project_01.homegym.model.Video;
import edu.ntut.project_01.homegym.repository.VideoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class HomeGymApplicationTests {
    @Autowired
    private VideoRepository videoRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void insertTest(){
        List<Video> vbList;
        Video vb;
        String row;
        String[] col;
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");

        vbList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("/Users/chin/Desktop/video.csv"))) {
            int times = 0;
            while ((row = br.readLine()) != null) {
                if (times != 0) {
                    vb = new Video();
                    col = row.split(",");
                    vb.setName(col[1]);
                    vb.setVideoInfo((col[2]).toCharArray());
                    vb.setCategory(col[3]);
                    vb.setPartOfBody(col[4]);
                    try {
                        vb.setTime(format.parse(col[6]));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    vb.setPrice(Integer.parseInt(col[7]));
                    vb.setEquipment(col[8]);
                    vb.setLevel(col[9]);
                    vb.setPass(Integer.parseInt(col[10]));
                    vb.setChecked(Integer.parseInt(col[11]));
                    vbList.add(vb);
                }
                times++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        videoRepository.saveAll(vbList);
        System.out.println("程式結束(Done...!!)");
    }

}
