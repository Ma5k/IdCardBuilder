package com;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import sun.misc.BASE64Encoder;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.List;
import java.util.*;

public class Windows {

    private static String file1 = "", file2 = "";

    public static String getPic(int type) {
        String name = "idz_nan1.jpg";
//        if (type == 1) { // 男
//            name = Util.idz_nan[(int) (Math.random()
//                    * (Util.idz_nan.length - 1) + 1)];
//        } else {
//            name = Util.idz_nv[(int) (Math.random() * (Util.idz_nv.length - 1) + 1)];
//        }
        return name;
    }

    public boolean createMark(String filePath, String outFile,
                              List<Map<String, Object>> list) {
        ImageIcon imgIcon = new ImageIcon(filePath);
        Image theImg = imgIcon.getImage();
        int width = theImg.getWidth(null);
        int height = theImg.getHeight(null);
        BufferedImage bimage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bimage.createGraphics();
        g.drawImage(theImg, 0, 0, null);
        for (int i = 0; i < list.size(); i++) {
            add(g, bimage, list.get(i));
        }
        g.dispose();
        try {
            FileOutputStream out = new FileOutputStream(outFile);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bimage);
            param.setQuality(70f, true);
            encoder.encode(bimage, param);
            out.close();
        } catch (Exception e) {
            return false;
        } finally {
            imgIcon = null;
            theImg = null;
        }
        return true;
    }

    public void add(Graphics2D g, BufferedImage bimage, Map<String, Object> map) {
        String markContent = (String) map.get("text");
        g.setColor((Color) map.get("color"));
        g.setBackground(Color.white);

        AttributedString ats = new AttributedString(markContent);
        Font f = new Font((String) map.get("fontType"), Font.BOLD,
                (Integer) map.get("fontSize"));
        ats.addAttribute(TextAttribute.FONT, f, 0, markContent.length());
        AttributedCharacterIterator iter = ats.getIterator();

        g.drawString(iter, (Integer) map.get("x"), (Integer) map.get("y")); // 添加水印的文字和设置水印文字出现的内容
    }

    public static boolean generateIdcodeZ(String baseDir, String tagerDir, Map<String, String> params) {
        boolean bool = false;
        try {
            Windows wm = new Windows();

            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            Map<String, Object> map = new HashMap<String, Object>();
            Color color1 = new Color(30, 30, 30);
            map.put("text", params.get("name")); // 姓名
            map.put("color", color1);
            map.put("x", 90);
            map.put("y", 48);
            map.put("fontType", "幼圆");
            map.put("fontSize", 18);
            list.add(map);

            char genderCode = params.get("code").charAt(16);
            int genderNum = Integer.parseInt(String.valueOf(genderCode));
            String gender = genderNum % 2 == 1?"男":"女";

            map = new HashMap<String, Object>();
            map.put("text", gender); // 性别
            map.put("color", color1);
            map.put("x", 90);
            map.put("y", 94);
            map.put("fontType", "幼圆");
            map.put("fontSize", 18);
            list.add(map);

            map = new HashMap<String, Object>();
            map.put("text", params.get("nation")); // 民族
            map.put("color", color1);
            map.put("x", 210);
            map.put("y", 94);
            map.put("fontType", "幼圆");
            map.put("fontSize", 18);
            list.add(map);

            String birthday = params.get("code").substring(6,14);
            String year = birthday.substring(0,4);
            String month = birthday.substring(4,6);
            String day = birthday.substring(6,8);

            map = new HashMap<String, Object>();
            map.put("text", year); // 年
            map.put("color", color1);
            map.put("x", 90);
            map.put("y", 136);
            map.put("fontType", "幼圆");
            map.put("fontSize", 18);
            list.add(map);

            map = new HashMap<String, Object>();
            map.put("text", month);// 月
            map.put("color", color1);
            map.put("x", 180);
            map.put("y", 136);
            map.put("fontType", "幼圆");
            map.put("fontSize", 18);
            list.add(map);

            map = new HashMap<String, Object>();
            map.put("text", day);// 日
            map.put("color", color1);
            map.put("x", 230);
            map.put("y", 136);
            map.put("fontType", "幼圆");
            map.put("fontSize", 18);
            list.add(map);

            String address = params.get("address");
            String[] adds = new String[2];
            if (address.length() > 11) {
                adds[0] = address.substring(0, 11);
                adds[1] = address.substring(11);
            } else {
                adds[0] = address;
                adds[1] = "";
            }
            map = new HashMap<String, Object>();
            map.put("text", adds[0]); // 证件地址1
            map.put("color", color1);
            map.put("x", 90);
            map.put("y", 184);
            map.put("fontType", "幼圆");
            map.put("fontSize", 18);
            list.add(map);
            if (address.length() > 11) {
                map = new HashMap<String, Object>();
                map.put("text", adds[1]);// 证件地址2
                map.put("color", color1);
                map.put("x", 90);
                map.put("y", 210);
                map.put("fontType", "幼圆");
                map.put("fontSize", 18);
                list.add(map);
            }

            map = new HashMap<String, Object>();
            map.put("text", params.get("code")); // 证件号码
            map.put("color", color1);
            map.put("x", 167);
            map.put("y", 296);
            map.put("fontType", "微软雅黑");
            map.put("fontSize", 24);
            list.add(map);

            if (gender.equals("男")) {
                file1 = baseDir + "\\images\\" + getPic(1);
            } else {
                file1 = baseDir + "\\images\\" + getPic(2);
            }
            wm.createMark(file1, tagerDir + "idz" + params.get("code")  + ".jpg", list);
            System.out.println("证件正面照【" + tagerDir + "idz" + params.get("code") + ".jpg"
                    + "】生成成功");
            Thread.sleep(200);
            bool = generateIdcodeF(wm, tagerDir + "idf" + params.get("code") + ".jpg", params.get("officeAddress"), params.get("validityPeriod"));
        } catch (Exception e) {
            e.printStackTrace();
            bool = false;
        }
        return bool;
    }

    public static boolean generateIdcodeF(Windows wm, String tager, String ress, String validityPeriod) {
        boolean bool = false;
        try {
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            Map<String, Object> map = new HashMap<String, Object>();
            Color color1 = new Color(30, 30, 30);
            map.put("text", ress);
            map.put("color", color1);
            map.put("x", 220);
            map.put("y", 263);
            map.put("fontType", "幼圆");
            map.put("fontSize", 18);
            list.add(map);

            map = new HashMap<String, Object>();
            map.put("text", validityPeriod);
            map.put("color", color1);
            map.put("x", 220);
            map.put("y", 310);
            map.put("fontType", "微软雅黑");
            map.put("fontSize", 18);
            list.add(map);
            wm.createMark(file2, tager, list);
            bool = true;
            System.out.println("证件反面照【" + tager + "】生成成功");
        } catch (Exception e) {
            e.printStackTrace();
            bool = false;
        }
        return bool;
    }

    public static void copyFormJar(Windows wm, String fileUrl, String dest) {
        try {
            // 返回读取指定资源的输入流
            InputStream in = wm.getClass().getResourceAsStream(fileUrl);
            int BUFF_SIZE = 100000;
            byte[] buffer = new byte[BUFF_SIZE];
            OutputStream out = null;
            try {
                out = new FileOutputStream(dest);
                while (true) {
                    synchronized (buffer) {
                        int amountRead = in.read(buffer);
                        if (amountRead == -1) {
                            break;
                        }
                        out.write(buffer, 0, amountRead);
                    }
                }
            } finally {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void byteToString(byte[] byt) {
        String s = new BASE64Encoder().encode(byt);
        System.out.print(s);
    }

    public static String getJarDir(String url) {
//		if (url != null && url.startsWith("file")) {
//			url = url.substring(6);
//			// System.out.println("-getJarDir-----------" + url);
//			url = url.substring(0, url.indexOf("!"));
//			// System.out.println("-getJarDir-----------" + url);
//			File jar = new File(url);
//			url = jar.getParentFile().getPath();
//			// System.out.println("-getJarDir-----------" + url);
//		}
//用idea运行调试时注释上面if，用下面这段语句
        File jar = new File(url);
        url = jar.getParentFile().getPath();
        return url;
    }

    public static void createDir(String dir) {
        if (dir != null) {
            File file = new File(dir);
            if (!file.exists() || file.isFile()) {
                file.mkdirs();
            }
        }
    }

    public static void help() {
        try {
            String str1 = "--------------身份证自动生成工具-----------------";
            String str2 = "----------------------------------------------";
            String str3 = "------此工具只用作开发测试之用，切勿用作非法用途------";
            String str4 = "退出程序指令：exit";
            String[] p1 = str1.split("");
            String[] p2 = str2.split("");
            String[] p3 = str3.split("");
            String[] p4 = str4.split("");
            for (int i = 0; i < p1.length; i++) {
                Thread.sleep(5);
                System.out.print(p1[i]);
            }
            System.out.println();
            for (int i = 0; i < p2.length; i++) {
                Thread.sleep(5);
                System.out.print(p2[i]);
            }
            System.out.println();
            for (int i = 0; i < p3.length; i++) {
                Thread.sleep(5);
                System.out.print(p3[i]);
            }
            System.out.println();
            for (int i = 0; i < p4.length; i++) {
                Thread.sleep(5);
                System.out.print(p4[i]);
            }
            System.out.println("\n");
        } catch (InterruptedException e) {
        }
    }

    public static void checkImage(Windows wm, String baseDir) {
        String file = null;
        File fileo1 = null;
        for (int i = 0; i < Util.idz_nan.length; i++) {
            file = baseDir + "\\images\\" + Util.idz_nan[i];
            fileo1 = new File(file);
            if (!fileo1.exists()) {
                copyFormJar(wm, "/" + Util.idz_nan[i], file);
            }
        }

        for (int i = 0; i < Util.idz_nv.length; i++) {
            file = baseDir + "\\images\\" + Util.idz_nv[i];
            fileo1 = new File(file);
            if (!fileo1.exists()) {
                copyFormJar(wm, "/" + Util.idz_nv[i], file);
            }
        }
        file2 = baseDir + "\\images\\" + "idf.jpg";
        File fileo2 = new File(file2);
        if (!fileo2.exists()) {
            copyFormJar(wm, "/idf.jpg", file2);
        }
    }

    public static int parseInt(String param, int dat){
        if(param != null){
            try {
                dat = Integer.parseInt(param);
            } catch (Exception e) {
                System.out.println(param + " 是非数字...默认给值 " + dat);
            }
        }
        return dat;
    }

    public static void main(String[] args) {
        try {
            help();
            Scanner sc = new Scanner(System.in);
            String arg = "";
            while (!arg.equals("exit")) {
                System.out.println("请输入需要生成身份证照片的数量（正反面一起算一份）...");
                arg = sc.nextLine();
                if(arg.equals("-help")){
                    help();
                    arg = sc.nextLine();
                } else if(arg.equals("exit")){
                    System.out.println("\n");
                    int c = 0;
                    String[] wel = {"谢", "谢", "使", "用", "!", "", "", "再", "见", "!"};
                    while(c < 10){
                        Thread.sleep(50);
                        if(c < 4){
                            System.out.print(wel[c]);
                        } else if(c > 4 && c < 7){
                            System.out.println();
                        } else {
                            System.out.print(wel[c]);
                        }
                        c++;
                    }
                    System.exit(-1);
                } else {
                    int num = parseInt(arg, 1);

                    Windows wm = new Windows();
                    String url = wm.getClass().getResource("/idf.jpg").getPath();
//                    String url = "E:\\code\\IdcardBuilder\\bin\\idf.jpg";
                    url = Util.StringDecode(url);
                    String baseDir = getJarDir(url);
                    createDir(baseDir + "\\images\\");
                    String newDir = baseDir + "\\idcode\\";

                    System.out.println("请输入需要要保存的路径（默认保存当前路径，若要默认直接按回车即可）...");
                    arg = sc.nextLine();
                    if (args.length > 1) {
                        newDir = args[1];
                    } else {
                        createDir(newDir);
                    }
                    checkImage(wm, baseDir);

                    Map<String, String> params = new HashMap<String, String >(8);

                    System.out.println("请输入身份证姓名：");
                    String name = sc.nextLine();
                    params.put("name", name);

                    System.out.println("请输入身份证号码：");
                    String code = sc.nextLine();
                    params.put("code",code);

                    System.out.println("请输入民族：");
                    String nation = sc.nextLine();
                    params.put("nation", nation);

                    System.out.println("住址：");
                    String address = sc.nextLine();
                    params.put("address", address);

                    System.out.println("请输入签发机关：");
                    String officeAddress = sc.nextLine();
                    params.put("officeAddress", officeAddress);

                    System.out.println("请输入有效期限：（格式为：yyyy.mm.dd-yyyy.mm.dd）");
                    String validityPeriod = sc.nextLine();
                    params.put("validityPeriod", validityPeriod);

                    generateIdcodeZ(baseDir, newDir, params);

                    System.out.println("\n☆☆☆☆☆☆☆☆ 完成 ☆☆☆☆☆☆☆☆\n");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
