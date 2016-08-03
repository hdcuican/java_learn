package com.netty.chapter07.protobuf;


/**
 * <p>Decsription: </p>
 * @author  shadow
 * @date  2016年8月7日
 */
public class TestSubscribeReqProto {
	
	private static byte[] encode(SubscribeReqProto.SubscribeReq req) {
		return req.toByteArray();
	}
	
	private static SubscribeReqProto.SubscribeReq decode(byte[] body) throws Exception {
		return SubscribeReqProto.SubscribeReq.parseFrom(body);
	}
	
	
	private static SubscribeReqProto.SubscribeReq createSubscribeReq() {
		SubscribeReqProto.SubscribeReq.Builder builder
				= SubscribeReqProto.SubscribeReq.newBuilder();
		builder.setSubReqID(1);
		builder.setAddrsss("swdswdw");
		builder.setProductName("ipad mini");
		builder.setUserName("shadow");
		return builder.build();
	}
	
	public static void main(String[] args) throws Exception {
		SubscribeReqProto.SubscribeReq req = createSubscribeReq();
		System.out.println("Before encode: " + req.toString());
		
		SubscribeReqProto.SubscribeReq req2 = decode(encode(req));
		System.out.println("After encode: " + req2.toString());
		
		System.out.println("Assert equal : --> " + req2.equals(req));
	}

}
