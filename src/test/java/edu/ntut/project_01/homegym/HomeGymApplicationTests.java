package edu.ntut.project_01.homegym;

import edu.ntut.project_01.homegym.exception.category.MemberNotExistException;
import edu.ntut.project_01.homegym.model.Course;
import edu.ntut.project_01.homegym.model.Member;
import edu.ntut.project_01.homegym.model.Orders;
import edu.ntut.project_01.homegym.repository.CourseRepository;
import edu.ntut.project_01.homegym.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HomeGymApplicationTests {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private JavaMailSender mailSender;

    @Test
    void contextLoads() {
    }

    @Test
    void insertTest() {
        List<Course> vbList;
        Course vb;
        String row;
        String[] col;
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");

        vbList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("/Users/chin/Desktop/video.csv"))) {
            int times = 0;
            while ((row = br.readLine()) != null) {
                if (times != 0) {
                    vb = new Course();
                    col = row.split(",");
                    vb.setCourseName(col[1]);
                    vb.setCourseInfo((col[2]).toCharArray());
                    vb.setCategory(col[3]);
                    vb.setPartOfBody(col[4]);
                    try {
                        vb.setUploadTime(format.parse(col[6]));
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
        courseRepository.saveAll(vbList);
        System.out.println("程式結束(Done...!!)");
    }


    @Test
    public void sendSimpleMail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("homegym_ntut_pj01@outlook.com");
        message.setTo("zhps7239@yahoo.com.tw");
        message.setSubject("主旨：JavaMail測試！！！！");
        message.setText("內容：這是一封測試信件，恭喜您成功發送了唷");

        mailSender.send(message);
    }

    @Transactional
    @Test
    public void fkTest() {
        Optional<Member> member = memberRepository.findMemberByMemberId(2);
//        System.out.println(member.getCoach().getExperience());

        if (member.isPresent()) {
            Set<Orders> orders = member.get().getOrders();
            for (Orders v : orders) {
                System.out.println(v.getCourses());
            }
        } else {
            throw new MemberNotExistException("用戶不存在");
        }
    }

    @Transactional
    @Test
    public void courseList() {
        Optional<Member> member = memberRepository.findById(6);
        Set<Course> myCourses;
        if (member.isPresent()) {
            Set<Orders> orders = member.get().getOrders();
            myCourses = new HashSet<>();
            for (Orders orderList : orders) {
                Set<Course> courses = orderList.getCourses();
                for (Course course : courses) {
                    System.out.println(course.getCourseName());
                    myCourses.add(course);
                }
            }
        }
    }

}
