package edu.ntut.project_01.homegym;

import edu.ntut.project_01.homegym.exception.category.MemberNotExistException;
import edu.ntut.project_01.homegym.model.Course;
import edu.ntut.project_01.homegym.model.Member;
import edu.ntut.project_01.homegym.model.Orders;
import edu.ntut.project_01.homegym.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HomeGymApplicationTests {


//    @Autowired
//    private JavaMailSender mailSender;
//    @Autowired
//    private CourseRepository courseRepository;
//    @Autowired
//    private CourseCommentRepository coursecCommentRepository;
//    @Autowired
//    private MemberRepository memberRepository;
//    @Autowired
//    private OrdersRepository ordersRepository;
//    @Autowired
//    private JavaMailSender mailSender;
////
//    @Test
//    void countComment() {
////        System.out.println(coursecCommentRepository.countStar(3));
//        File file = new File("/coachImages/coachImages1.jpg");
//        System.out.println("file是否存在"+file.exists());
//        System.out.println("file是否可讀"+file.canRead());
//        System.out.println("file是否可讀"+file.canRead());
//        File file4 = new File("/coachImages/coachImages1.jpg");
//        System.out.println("file2是否存在"+file4.exists());
//        System.out.println("file2是否可讀"+file4.canRead());
//        File file3 = new File("/src/main/resources/static/coachImages/coachImages1.jpg");
//        System.out.println("file2是否存在"+file3.exists());
//        System.out.println("file2是否可讀"+file3.canRead());
//        File file2 = new File("C:/Users/USER/Desktop/HomeGym_SpringBoot/src/main/resources/static/coachImages/coachImages1.jpg");
//        System.out.println("file2是否存在"+file2.exists());
//        System.out.println("file2是否可讀"+file2.canRead());
//    }
//
//    @Test
//    void insertTest() {
//        List<Course> vbList;
//        Course vb;
//        String row;
//        String[] col;
//        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
//
//        vbList = new ArrayList<>();
//
//        try (BufferedReader br = new BufferedReader(new FileReader("/Users/chin/Desktop/video.csv"))) {
//            int times = 0;
//            while ((row = br.readLine()) != null) {
//                if (times != 0) {
//                    vb = new Course();
//                    col = row.split(",");
//                    vb.setCourseName(col[1]);
//                    vb.setCourseInfo((col[2]));
//                    vb.setPartOfBody(col[4]);
//                    try {
////                        vb.setUploadTime(format.parse(col[6]));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    vb.setPrice(Integer.parseInt(col[7]));
//                    vb.setEquipment(col[8]);
//                    vb.setLevel(col[9]);
//                    vb.setPass(Integer.parseInt(col[10]));
//                    vb.setChecked(Integer.parseInt(col[11]));
//                    vbList.add(vb);
//                }
//                times++;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        courseRepository.saveAll(vbList);
//        System.out.println("程式結束(Done...!!)");
//    }
//
//    @Autowired
//    private FQARepository fQARepository;
//
//    @Test
//    public void sendSimpleMail() {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("homegym_ntut_pj01@outlook.com");
//        message.setTo("zhps7239@yahoo.com.tw");
//        message.setSubject("主旨：JavaMail測試！！！！");
//        message.setText("內容：這是一封測試信件，恭喜您成功發送了唷");
//
//        mailSender.send(message);
//    }
//
//    @Transactional
//    @Test
//    public void fkTest() {
//        Optional<Member> member = memberRepository.findMemberByMemberId(2);
////        System.out.println(member.getCoach().getExperience());
//
//        if (member.isPresent()) {
//            Set<Orders> orders = member.get().getOrders();
//            for (Orders v : orders) {
//                System.out.println(v.getCourses());
//            }
//        } else {
//            throw new MemberNotExistException("用戶不存在");
//        }
//    }
//
//    @Transactional
//    @Test
//    public void courseList() {
//        Optional<Member> member = memberRepository.findById(6);
//        Set<Course> myCourses;
//        if (member.isPresent()) {
//            Set<Orders> orders = member.get().getOrders();
//            myCourses = new HashSet<>();
//            for (Orders orderList : orders) {
//                Set<Course> courses = orderList.getCourses();
//                for (Course course : courses) {
//                    System.out.println(course.getCourseName());
//                    myCourses.add(course);
//                }
//            }
//        }
//    }
//    @Transactional
//    @Test
//    public void orderStatusOK(){
//        Collection<String> status = new HashSet<>();
//        status.add("付款完成");
//        Integer memberId = 7;
////        Optional<List<Orders>> orders = ordersRepository.findOrdersByOrderStatusIn(status);
//        PageRequest pageRequest = PageRequest.of(0,2);
//        Page<Orders> orders = ordersRepository.findOrdersByOrderStatusIn(status,pageRequest);
//        for (Orders o : orders){
//            System.out.println("<-------訂單編號------->");
//            System.out.println(o.getOrderId());
//            System.out.println("//////////////////////////");
//            System.out.println("<-------課程集合------->");
//            System.out.println(o.getCourses());
//            System.out.println("//////////////////////////");
//            System.out.println("<-------會員ID------->");
//            System.out.println(o.getMember().getMemberId());
//            System.out.println("/////////////////////////");
//        }
//    }
//        @Test
//    public void sendTemplateMail() throws Exception {
//        MimeMessage mimeMessage = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
//        helper.setFrom("homegym_ntut_pj01@outlook.com");
//        helper.setTo("lihanchin@gmail.com");
//        helper.setSubject("主旨：測試測試 模版信件");
//
//        String html = "<html><body><div><div><img src=\"cid:logoPic\"/></div><div><div>" +
//                "請點擊下方，重新設置帳戶密碼。</div><button><a href=\"" + "fkgfjp" + "\"/>重設密碼</button><br/>" +
//                "<div>提醒您：重設密碼後，請妥善保管並避免再次忘記。<br/>有任何問題，歡迎來電洽詢HomeGym團隊客服專線0911-222-333。<br/></div>" +
//                "<br/><div>厝動HomeGym服務團隊 敬上</div></div></div></body></html>";
//
//        helper.setText(html, true);
//        FileSystemResource file = new FileSystemResource(new File("src/main/resources/static/imag/hg_logo/logoMail.png"));
//        helper.addInline("logoPic", file);
//
//        mailSender.send(mimeMessage);
//    }
}
