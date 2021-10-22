package edu.ntut.project_01.homegym;

import java.text.SimpleDateFormat;

public class test {
    public static void main(String[] args) {

        String s = "1992-10-3";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.sql.Date date = new java.sql.Date(sdf.parse(s).getTime());
            System.out.println(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
