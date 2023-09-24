package com.rozwork.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * 错误信息处理类。
 *
 * @author rozwork
 */
public class ExceptionUtil
{
    /**
     * 获取exception的详细错误信息。
     */
    public static String getExceptionMessage(Throwable e)
    {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw, true));
        return sw.toString();
    }

    public static String formatException(Exception e) {
        StackTraceElement[] stackTraceElementArray = e.getStackTrace();
        StackTraceElement stackTraceElement = stackTraceElementArray[0];
        StringBuffer sb = new StringBuffer();
        sb.append(e);
        sb.append("\r\n");
        sb.append("     ");
        sb.append("at");
        sb.append(" ");
        sb.append(stackTraceElement.getClassName());
        sb.append(".");
        sb.append(stackTraceElement.getMethodName());
        sb.append("(");
        sb.append(stackTraceElement.getFileName());
        sb.append(":");
        sb.append(stackTraceElement.getLineNumber());
        sb.append(")");
        System.err.println(sb.toString());
        return sb.toString();
    }

    public static String getRootErrorMessage(Exception e)
    {
        Throwable root = ExceptionUtils.getRootCause(e);
        root = (root == null ? e : root);
        if (root == null)
        {
            return "";
        }
        String msg = root.getMessage();
        if (msg == null)
        {
            return "null";
        }
        return StringUtils.defaultString(msg);
    }
}
