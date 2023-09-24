package com.rozwork.common.utils.uuid;

/**
 * ID生成器工具类
 * 
 * @author rozwork
 */
public class IdUtils
{
    /**
     * 获取随机UUID
     * 
     * @return 随机UUID
     */
    public static String randomUUID()
    {
        return UUID.randomUUID().toString();
    }

    /**
     * 简化的UUID，去掉了横线
     * 
     * @return 简化的UUID，去掉了横线
     */
    public static String simpleUUID()
    {
        return UUID.randomUUID().toString(true);
    }

    /**
     * 获取随机UUID，使用性能更好的ThreadLocalRandom生成UUID
     * 
     * @return 随机UUID
     */
    public static String fastUUID()
    {
        return UUID.fastUUID().toString();
    }

    /**
     * 简化的UUID，去掉了横线，使用性能更好的ThreadLocalRandom生成UUID
     * 
     * @return 简化的UUID，去掉了横线
     */
    public static String fastSimpleUUID()
    {
        return UUID.fastUUID().toString(true);
    }

    /**
	 *
	 * (获取指定长度uuid)
	 * 
	 * @return
	 */
	public static String getUUID(int len)
	{
		if(0 >= len)
		{
			return null;
		}
		
		String uuid = fastSimpleUUID();
		System.out.println(uuid);
		StringBuffer str = new StringBuffer();
		
		for (int i = 0; i < len; i++)
		{
			str.append(uuid.charAt(i));
		}
		
		return str.toString();
	}
    
    /*
     * 获取时间戳的id
     */
    public static String generateNo(String prefix, boolean isDateTime, int len) {
        String ret = "";

        if(prefix != null) {
            ret = prefix + ret;
        }

        if(isDateTime) {
            ret = ret + com.rozwork.common.utils.DateUtils.dateTimeNow();
        }

        return ret + getUUID(len);
    }

    /*
     * 获取时间戳的id
     */
    public static String generateNo(String prefix) {
        return generateNo(prefix, true, 9);
    }
    
    /*
     * 获取时间戳的id
     */
    public static String generateNo() {
        return generateNo("rhro", true, 8);
    }

    /*
     * 获取时间戳的id
     */
    public static String generateNumber(String prefix, boolean isDateTime, int len) {
        String ret = "";

        if(prefix != null) {
            ret = prefix + ret;
        }

        if(isDateTime) {
            ret = ret + com.rozwork.common.utils.DateUtils.dateTimeNow();
        }

        return ret + generateLimitedDigitsUUID(len);
    }

    public static String generateLimitedDigitsUUID(int numDigits) {
        UUID uuid = UUID.randomUUID();
        long numericUUID = Math.abs(uuid.getMostSignificantBits()) % (long) Math.pow(10, numDigits);
        return String.format("%0" + numDigits + "d", numericUUID);
    }
}
