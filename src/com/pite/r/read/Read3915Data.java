package com.pite.r.read;

import java.util.ArrayList;
import java.util.List;

import com.pite.r.model.Pite3915ModelDT;
import com.pite.r.model.Pite3915ModelDT.ModelWorkDT;
import com.pite.r.model.Pite3915ModelID;
import com.pite.r.model.Pite3915ModelID.ModelWorkID;
import com.pite.r.prol.TestRProl;
import com.pite.r.tool.TestRCMD;
import com.pite.r.util.HostUtils;

import android.util.Log;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

/**
 * 3915数据读取类
 */
public class Read3915Data implements ModelWorkDT, ModelWorkID {
	private ChannelHandlerContext ctx;
	private Pite3915ModelDT piteDT = new Pite3915ModelDT(this);
	private Pite3915ModelID piteID = new Pite3915ModelID(this);
	public List<byte[]> addDT = new ArrayList<byte[]>();
	private int sizelength = 0;
	private byte[] headbt = null;
	private int pakLength = 0;

	public void addBytes(byte[] bt, ChannelHandlerContext ctx) {
		this.ctx = ctx;
		piteID.addBytes(bt);
		piteDT.addBytes(bt);
	}

	@Override
	public void afterWorkID(byte[] bt) {
		headbt = bt;
		sizelength = 0;
		addDT.clear();
		for (int i = 11; i < bt.length; i++) {
			sizelength *= 10;
			sizelength += (bt[i] - 0x30);
		}
		pakLength = sizelength;
	}

	@Override
	public void afterWorkDT(byte[] bt, int len) {
		setUpdata(bt, len);
	}

	/**
	 * 要发送的数据
	 * 
	 * @param bt
	 *            //数据 DT
	 * @param len
	 *            长度
	 * @param sizelength
	 * 
	 * @param pakLength
	 *            数据总长度
	 * @param headbt
	 *            ID
	 */
	public void setUpdata(byte[] bt, int len) {
		this.sizelength -= len;
		// Log.e("1", "剩余长度：" + this.sizelength + " len " + len);
		if (sizelength != 0) {
			byte[] nextbt = TestRCMD.getNextPacket(4); // 下一包
			for (int i = 0; i < 2; i++) {
				ctx.writeAndFlush(Unpooled.copiedBuffer(nextbt));
			}
			byte[] addList = new byte[bt.length - 14];
			System.arraycopy(bt, 11, addList, 0, addList.length);
			addDT.add(addList);
			// Log.e("4", "分包后的数据：" + addList.length);
		} else {
			int lenth = 0;
			byte[] sendBt = null;
			for (int i = 0; i < addDT.size(); i++) {
				lenth += addDT.get(i).length;
			}
			sendBt = new byte[lenth + bt.length - 14];
			int index = 0;
			for (byte[] bts : addDT) {
				System.arraycopy(bts, 0, sendBt, index, bts.length);
				// Log.e("1", "整包数据：" +
				// SerialPortService.bytesToHexString(bts));
				index += bts.length;
			}
			// Log.e("1", "send长度：" + sendBt.length);
			System.arraycopy(bt, 11, sendBt, index, bt.length - 14);

			byte[] fullbt = new byte[sendBt.length + headbt.length + 11 + 3];
			System.arraycopy(headbt, 0, fullbt, 0, headbt.length);
			byte[] DTBt = TestRCMD.setHeader(pakLength, 1);
			System.arraycopy(DTBt, 0, fullbt, headbt.length, DTBt.length);
			System.arraycopy(sendBt, 0, fullbt, headbt.length + DTBt.length, sendBt.length);

			int check = 0;
			for (int i = headbt.length + DTBt.length; i < fullbt.length - 3; i++) {
				check += fullbt[i] < 0 ? fullbt[i] & 0xff : fullbt[i];
			}
			check %= 256;
			byte[] checkBt = TestRCMD.zeroBt(check + "", 3);
			System.arraycopy(checkBt, 0, fullbt, fullbt.length - 3, 3);
			// Log.e("1", "最后的整包数据：" +
			// SerialPortService.bytesToHexString(fullbt) + " \n" +
			// fullbt.length);
			setFullData(fullbt);
		}
	}

	/**
	 * 最后的数据
	 * 
	 * @param bt
	 */
	public void setFullData(byte[] bt) {
		switch (bt[10]) {
		case 0x35:
			int hostId = 0;
			for (int i = 3; i < 9; i++) {
				hostId *= 10;
				hostId += (bt[i] - 0x30);
			}
			HostUtils.hostID = hostId; // 设置主机号
			byte[] redSuess = TestRCMD.set0x38Connect();
			ctx.writeAndFlush(Unpooled.copiedBuffer(redSuess));
			Log.e("2", "连接成功响应命令：" + TestRCMD.bytesToHexString(bt));
			break;
		case 0x34:
			if (bt[0] == 0x7E) {
				byte[] prlo = new byte[bt.length - 29 - 3];
				System.arraycopy(bt, 29, prlo, 0, prlo.length);
				byte[] redSuesss = TestRCMD.set0x38Connect();
				ctx.writeAndFlush(Unpooled.copiedBuffer(redSuesss));
				TestRProl testRProl = new TestRProl();
				if (testRProl.addBytes(prlo, pakLength)) {
					HostUtils.testData.setHandUpData(1, bt, testRProl);
				}
			}
			break;
		}
	}
}
