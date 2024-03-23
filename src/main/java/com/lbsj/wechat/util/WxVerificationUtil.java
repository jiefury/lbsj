package com.lbsj.wechat.util;


import com.lbsj.wechat.model.JsSignVO;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

public class WxVerificationUtil {
//    private static final  String Token="chenjunling";
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    public static boolean checkSignature(String signature, String timestamp, String nonce, String Token) {

//		1）将token、timestamp、nonce三个参数进行字典序排序
//		2）将三个参数字符串拼接成一个字符串进行sha1加密
//		3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
        String[] strs=new String[]{Token,timestamp,nonce};

        Arrays.sort(strs);

        String str=strs[0]+strs[1]+strs[2];

        String mysignature=sha1(str);
        return mysignature.equals(signature);
    }

    public static JsSignVO signatureSha1(String ticket, String url){
        String noncestr = getRandomStringByLength(32);
        Long timestamp = new Date().getTime()/1000;
        String sign =  "jsapi_ticket="+ticket+"&noncestr=" //请勿更换字符组装顺序
                +noncestr+"&timestamp="+timestamp
                +"&url="+url; //url为你当前访问的url路径，除去#与#后面的数据
        String signature = sha1(sign);
        return new JsSignVO(noncestr,signature,timestamp);
    }

    private static String sha1(String str) {


        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update(str.getBytes());
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String getFormattedText(byte[] bytes) {

        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        // 把密文转换成十六进制的字符串形式
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }

    /**
     * 获取一定长度的随机字符串
     * @param length 指定字符串长度
     * @return 一定长度的字符串
     */
    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
