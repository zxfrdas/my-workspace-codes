package bugreport;


import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HMACSHA1
{
	private static final String HMAC_SHA256 = "HmacSHA256";

	/**
	 * 生成签名数据
	 * 
	 * @param data
	 *            待加密的数据
	 * @param key
	 *            加密使用的key
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 */
	public static String getSignature(String data, String key) throws Exception
	{
		SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA256);
		signingKey.getAlgorithm();
		Mac mac = Mac.getInstance(HMAC_SHA256);
		mac.init(signingKey);
		byte[] rawHmac = mac.doFinal(data.getBytes());
		String oauth = byteArrayToHexString(rawHmac);
		return oauth;
	}

	/**
	 * 将字节转换为十六进制字符串
	 * @param ib byte
	 * @return String
	 */
	private static String byteToHexString(byte ib)
	{
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
				'f' };
		char[] ob = new char[2];
		ob[0] = Digit[(ib >>> 4) & 0X0F];
		ob[1] = Digit[ib & 0X0F];

		return new String(ob);
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 * @param bytearray byte[]
	 * @return String
	 */
	private static String byteArrayToHexString(byte[] bytearray)
	{
		String strDigest = "";

		for (int i = 0; i < bytearray.length; i++)
		{
			strDigest += byteToHexString(bytearray[i]);
		}

		return strDigest;
	}
}
