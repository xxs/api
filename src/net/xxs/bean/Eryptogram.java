package net.xxs.bean;

import java.security. *;

//import java_cup.internal_error;

import javax.crypto. *;
/**
*加密解密类
*@authorWangHu
*@version1.002004年03月18日
*/

public class Eryptogram
{

    private/* static*/ String Algorithm ="DES";
    private /*static*/ String ciAlgorithm = "DES/ECB/NoPadding";
    //定义加密算法,可用DES,DESede,Blowfish
    /*static*/ boolean debug =false ;
    /*static*/ SecretKey deskey;
    public void setDeskey(byte[] key) {
    	  deskey =  new javax.crypto.spec.SecretKeySpec (key ,Algorithm );   
	}
	/**
    *构造子注解.
    */
    public Eryptogram ()
    {
    }
    /**
    *生成密钥
    *@returnbyte[]返回生成的密钥
    *@throwsexception扔出异常.
    */
    public/* static*/ byte []getSecretKey ()throws Exception 
    {
        KeyGenerator keygen =KeyGenerator.getInstance (Algorithm );
        SecretKey deskey =keygen.generateKey ();
        if (debug )System.out.println ("生成密钥:"+byte2hex (deskey.getEncoded ()));
        return deskey.getEncoded ();
    }
    
    /**
    *异或
    *@paraminput需要异或的数据
    *@returnbyte[]返回生成的数据
    */
 
    public/* static*/ byte[] do_xor(byte[] desByte,byte[] srByte)
    {
    	int ld = desByte.length;
    	int ls = srByte.length;
    	int l,lm;
    	if(ld < ls){
    		l = ld;
    		lm = ls;
    	}
    	else{
    		l = ls;
    		lm = ld;
    	}
		byte[] res = new byte[lm];
    	
       	if(ld < ls){
    		for(int i=0;i<lm;i++)
    			res[i] = srByte[i];
    	}
    	else{
    		for(int i=0;i<lm;i++)
    			res[i] = desByte[i];
    	}
    	
    	for(int i=0;i<l;i++){
    		res[i] = (byte) (desByte[i] ^ srByte[i]);    		
    	}
    	return res;
    }
    /**
    *将指定的数据根据提供的密钥进行加密
    *@paraminput需要加密的数据
    *@paramkey密钥
    *@returnbyte[]加密后的数据
    *@throwsException
    */
    public/* static*/ byte []encryptData (byte []input  )throws Exception 
    {
        if (debug )
        {
            System.out.println ("加密前的二进串:"+byte2hex (input ));
            System.out.println ("加密前的字符串:"+new String (input ));
        }
        Cipher c1 =Cipher.getInstance (ciAlgorithm);
        c1.init (Cipher.ENCRYPT_MODE ,deskey );
        byte []cipherByte =c1.doFinal (input );
        if (debug )System.out.println ("加密后的二进串:"+byte2hex (cipherByte ));
        return cipherByte ;
    }
    
    /**
     *对任意的数据进行加密
     *@paraminput需要加密的数据
     *@paramkey密钥
     *@returnbyte[]加密后的数据
     *@throwsException
     */
     public/* static*/ byte []DesEncryptData (byte []input  )throws Exception 
     {
         byte[] ba = new byte[8];
         byte[] btmp = new byte[8];
         int len = input.length + 8 - (input.length % 8);
         byte[] br = new  byte[len];
    	 for(int i=0;i<input.length;){
    		 for(int j=0;j<8;j++){
    			 if((i+j)<input.length)
    				 ba[j] = input[i+j];
    			else ba[j] = 0;
    		 }
    		 btmp = encryptData(ba);
    		 for(int j=0;j<8;j++){
    				 br[i+j] = btmp[j];
    		 }
    		 i=i+8;
    		 
        	 
         }
         return br ;
     }
    
    /**
    *将给定的已加密的数据通过指定的密钥进行解密
    *@paraminput待解密的数据
    *@paramkey密钥
    *@returnbyte[]解密后的数据
    *@throwsException
    */
    public /*static*/ byte []decryptData (byte []input)throws Exception 
    {
        //=new javax.crypto.spec.SecretKeySpec (key ,Algorithm );
        if (debug )System.out.println ("解密前的信息:"+byte2hex (input ));
        Cipher c1 =Cipher.getInstance (ciAlgorithm );
        c1.init (Cipher.DECRYPT_MODE ,deskey );
        byte []clearByte =c1.doFinal (input );
        if (debug )
        {
            System.out.println ("解密后的二进串:"+byte2hex (clearByte ));
            System.out.println ("解密后的字符串:"+(new String (clearByte )));
        }
        return clearByte ;
    }
    
    
    /**
     *对任意已加密的数据进行解密
     *@paraminput待解密的数据
     *@paramkey密钥
     *@returnbyte[]解密后的数据
     *@throwsException
     */
     public /*static*/ byte []DesDecryptData (byte []input)throws Exception 
     {
         byte[] ba = new byte[8];
         byte[] btmp = new byte[8];
         int len = input.length + 8 - (input.length % 8);
         byte[] br = new  byte[len];
    	 for(int i=0;i<input.length;i+=8){
    		 for(int j=0;j<8;j++){
    			 if((i+j)<input.length)
    				 ba[j] = input[i+j];
    			else ba[j] = 0;
    		 }
    		 btmp = decryptData(ba);
    		 for(int j=0;j<8;j++){
    				 br[i+j] = btmp[j];
    		 }
    		 i=i+8;
    		 
        	 
         }
         return br ;
     }
     
     /**
      *计算MAC
      *@paraminput MAC串
      *@paramkey密钥
      *@return byte[]MAC值
      *@throwsException
      */
     
     public /*static*/ byte []GetMac (byte []input)throws Exception 
     {
    	 int nBlock;
    	 
    	 nBlock = input.length /8  + 1;
    	 byte[] temp = new byte[8];
    	 for(int i=0;i<nBlock;i++){
    		 for(int j=0;j<8;j++){
    			 if(i*8 + j < input.length)
    				  temp[j] ^= input[i*8+j];   
    			 else break;
    		 }
    		 temp = encryptData(temp);
    	 }
    	 return temp;
     
     }
     
 
    
    /**
    *字节码转换成16进制字符串
    *@parambyte[]b输入要转换的字节码
    *@returnString返回转换后的16进制字符串
    */
    public/* static*/ String byte2hex (byte []b )
    {
        String hs ="";
        String stmp ="";
        for (int n =0 ;n <b.length ;n ++)
        {
            stmp =(java.lang.Integer.toHexString (b [n ]&0XFF ));
            if (stmp.length ()==1 )hs =hs +"0"+stmp ;
            else hs =hs +stmp ;
            //if (n <b.length -1 )hs =hs +":";
        }
        return hs.toUpperCase ();
    }
    
    
    /**
    *16进制字符串转换成字节码
    *@paramString返回转换后的16进制字符串
    *@return byte[]ba输入要转换的字节码
    */
    public /*static */byte[] hextobyte (String hs )
    {
        byte ba[] = new byte[hs.length()/2];
        if((hs.length() % 2 )!=0)
       return ba;
        hs.toUpperCase();
        byte b;
        char c;
        for (int n =0;n <hs.length() ;n ++,n++)
        {
        	for(int i=0;i<2;i++){
        		c = hs.charAt(n+i);
        		b=0;
        		if(c >= '0' && c <='9')
        			b = (byte) ((byte) c - (byte)'0');
        		else if(c >= 'A' && c <='F')
        			b = (byte) ((byte) c - (byte)'A' + 10);
        		ba[n/2] = (byte) (ba[n/2] * 16 + b) ;
        	}
         }
        return ba;
    }
    
    /**
	 * 使用 MD5 进行加密计算
	 * 
	 * @param plainText
	 *            普通未加密的字符串
	 * 
	 * @return MD5 加密后的字符串
	 */
	public/* static*/ String md5(String plainText) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");

			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}

			return buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return "";
	}

    /*
    public static void main (String []args )
    {
        try 
        {
           // debug =false ;
        	
            Eryptogram etg =new Eryptogram ();
            etg.debug = true;
            //byte []key = etg.getSecretKey ();
             
            int a=7;
            int b=a/8;
            byte aaa[] = etg.hextobyte("123456");
            for(int i=0;i<aaa.length;i++)
            	System.out.print(aaa[i]+ "|");
            byte key[] = "11111111".getBytes();
            etg.setDeskey(key);
            for(int i=0;i<key.length;i++)
            	System.out.print(key[i]+ " ");
            System.out.print("Key=["+key.toString());
            key.toString();
            System.out.println ("\nkey==="+etg.byte2hex(key) );
            String aa ="root";
           // String aa ="12345678jrtjjfxt";
            byte []data =aa.getBytes ();
            System.out.println ("DATA="+data );
            byte []en =etg.DesEncryptData (data );
             System.out.println ("encryptData="+new String (en ));
            byte []de =etg.DesDecryptData (en);
            System.out.println ("decryptData="+new String (de ));
            System.out.println ("MAC="+new String (etg.byte2hex(etg.GetMac("1111111".getBytes())) )); 
            byte [] ssss = etg.hextobyte( "9BC5E9D420953654");
            
            System.out.println ("9BC5E9D420953654 ="+ new String(etg.DesDecryptData(ssss))); 
        }
        catch (Exception e )
        {
            e.printStackTrace ();
        }
      
    }  */
}