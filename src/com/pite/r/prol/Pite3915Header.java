package com.pite.r.prol;

import struct.StructClass;
import struct.StructField;
/**
 * 3915长数据结构 数据 Header
 */
@StructClass
public class Pite3915Header {
	@StructField(order = 0)
	public short Equipment;
	@StructField(order = 1)
	public byte DataType;
	@StructField(order = 2)
	public byte Version;
	@StructField(order = 3)
	public byte[] CreateTime = new byte[6];
	@StructField(order = 4)
	public byte[] pak = new byte[2];
}
