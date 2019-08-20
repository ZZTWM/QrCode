package com.how2java;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
 
import javax.imageio.ImageIO;
 
import com.swetake.util.Qrcode;
 
import jp.sourceforge.qrcode.QRCodeDecoder;
import jp.sourceforge.qrcode.data.QRCodeImage;
import jp.sourceforge.qrcode.exception.DecodingFailedException;
 
public class QRCodeUtil {
 
     public static void qrCodeEncode(String encodeddata, File destFile) throws IOException { 
            Qrcode qrcode = new Qrcode(); 
            qrcode.setQrcodeErrorCorrect('M');  // ������L 7%��M 15%��Q 25%��H 30%���Ͱ汾�й� 
            qrcode.setQrcodeEncodeMode('B');     
            qrcode.setQrcodeVersion(7);     // ����Qrcode���İ汾 
               
            byte[] d = encodeddata.getBytes("GBK"); // �ַ��� 
            BufferedImage bi = new BufferedImage(139, 139, BufferedImage.TYPE_INT_RGB); 
            // createGraphics   // ����ͼ�� 
            Graphics2D g = bi.createGraphics(); 
               
            g.setBackground(Color.WHITE);   // ���ñ�����ɫ����ɫ�� 
            g.clearRect(0, 0, 139, 139);    // ���� X��Y��width��height 
            g.setColor(Color.BLACK);    // ����ͼ����ɫ����ɫ�� 
       
            if (d.length > 0 && d.length < 123) { 
                boolean[][] b = qrcode.calQrcode(d); 
                for (int i = 0; i < b.length; i++) { 
                    for (int j = 0; j < b.length; j++) { 
                        if (b[j][i]) { 
                            g.fillRect(j * 3 + 2, i * 3 + 2, 3, 3); 
                        } 
                    } 
                } 
            } 
               
//        Image img = ImageIO.read(new File("D:/tt.png"));  logo 
//        g.drawImage(img, 25, 55,60,50, null); 
                       
            g.dispose(); // �ͷŴ�ͼ�ε��������Լ���ʹ�õ�����ϵͳ��Դ������ dispose ֮�󣬾Ͳ�����ʹ�� Graphics ���� 
            bi.flush(); // ˢ�´� Image ��������ʹ�õ����п��ع�����Դ 
       
            ImageIO.write(bi, "png", destFile); 
//          System.out.println("Input Encoded data is��" + encodeddata); 
        } 
       
        /**
         * ������ά�룬���ؽ�������
         * 
         * @param imageFile
         * @return
         */ 
        public static String qrCodeDecode(File imageFile) { 
            String decodedData = null; 
            QRCodeDecoder decoder = new QRCodeDecoder(); 
            BufferedImage image = null; 
            try { 
                image = ImageIO.read(imageFile); 
            } catch (IOException e) { 
                System.out.println("Error: " + e.getMessage()); 
            } 
       
            try { 
                decodedData = new String(decoder.decode(new J2SEImage(image)), "GBK"); 
//              System.out.println("Output Decoded Data is��" + decodedData); 
            } catch (DecodingFailedException dfe) { 
                System.out.println("Error: " + dfe.getMessage()); 
            } catch (UnsupportedEncodingException e) { 
                e.printStackTrace(); 
            } 
            return decodedData; 
        } 
       
        static class J2SEImage implements QRCodeImage { 
            BufferedImage image; 
           
            public J2SEImage(BufferedImage image) { 
                this.image = image; 
            } 
           
            public int getWidth() { 
                return image.getWidth(); 
            } 
           
            public int getHeight() { 
                return image.getHeight(); 
            } 
           
            public int getPixel(int x, int y) { 
                return image.getRGB(x, y); 
            } 
        } 
         
        public static void main(String[] args) { 
            String filePath = "d:/qrcode.png"; 
            File qrFile = new File(filePath); 
       
            // ��ά������ 
            String encodeddata = "���ڸ���"; 
            try { 
                QRCodeUtil.qrCodeEncode(encodeddata, qrFile); 
            } catch (IOException e) { 
                e.printStackTrace(); 
            } 
       
            // ���� 
            String reText = QRCodeUtil.qrCodeDecode(qrFile); 
            System.out.println(reText); 
        } 
}
