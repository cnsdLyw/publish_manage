package com.litc.common.util.html;

import java.io.InputStream;

/**
 * <p>Title: DumpStringStream</p>
 * <p>Description: 将字符串当作一个Stream
* JDK中的类似的类是过时的，被迫做一个</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * @author zhongying
 * @version 1.0
 */
public class DumpStringStream
    extends InputStream
{
    private byte value[];
    private int pos;
    private int count;

    /**
     *
     * @param content String
     * @param enc String
     */
    public DumpStringStream(String content, String enc)
    {
        value = null;
        count = 0;
        try
        {
            value = content.getBytes(enc);
        }
        catch (Exception _ex)
        {
            value = content.getBytes();
        }
        count = value.length;
    }

    public synchronized int available()
    {
        return count - pos;
    }

    public int length()
    {
        return count;
    }

    public synchronized int read()
    {
        if (value == null)
        {
            return -1;
        }
        else
        {
            return pos >= count ? -1 : value[pos++] & 0xff;
        }
    }

    public synchronized int read(byte b[], int off, int len)
    {
        if (value == null)
        {
            return -1;
        }
        if (b == null)
        {
            throw new NullPointerException();
        }
        if (off < 0 || off > b.length || len < 0 || off + len > b.length ||
            off + len < 0)
        {
            throw new IndexOutOfBoundsException();
        }
        if (pos >= count)
        {
            return -1;
        }
        if (pos + len > count)
        {
            len = count - pos;
        }
        if (len <= 0)
        {
            return 0;
        }
        for (int cnt = len; --cnt >= 0; )
        {
            b[off++] = value[pos++];

        }
        return len;
    }

    public synchronized void reset()
    {
        pos = 0;
    }

    public synchronized long skip(long n)
    {
        if (n < 0L)
        {
            return 0L;
        }
        if (n > (long) (count - pos))
        {
            n = count - pos;
        }
        pos += n;
        return n;
    }

}
