package com.netty.learning;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

public class MySuperHttpHandler extends SimpleChannelInboundHandler<Object> {
	private static final byte[] CONTENT = { 'f','u' };

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {

		ctx.flush();
	}

	@Override
	public void channelRead0(ChannelHandlerContext ctx, Object msg) {

		byte[] responseContent = null;
		if (msg instanceof HttpRequest) {
			HttpRequest req = (HttpRequest) msg;

			String reqUrl = req.getUri();

			System.out.println(reqUrl);

			// do something further with request here ...

			// this is the response part
			if (HttpHeaders.is100ContinueExpected(req)) {
				ctx.write(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
						HttpResponseStatus.CONTINUE));
			}

			boolean keepAlive = HttpHeaders.isKeepAlive(req);
			
			if(reqUrl.contains("request")){
				responseContent = new ProcessRequest(req).process();
			}
			FullHttpResponse response = new DefaultFullHttpResponse(
					HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
					Unpooled.wrappedBuffer(responseContent));

			response.headers()
					.set(HttpHeaders.Names.CONTENT_TYPE, "text/html");
			response.headers().set(HttpHeaders.Names.CONTENT_LENGTH,
					response.content().readableBytes());

			if (!keepAlive) {
				ctx.write(response).addListener(ChannelFutureListener.CLOSE);
			} else {
				response.headers().set(HttpHeaders.Names.CONNECTION,
						HttpHeaders.Values.KEEP_ALIVE);

				ctx.write(response);
			}
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		ctx.close();
	}

}
