//package gambol.examples.nio.netty;
//
//import org.jboss.netty.bootstrap.ServerBootstrap;
//import org.jboss.netty.buffer.ChannelBuffer;
//import org.jboss.netty.buffer.ChannelBuffers;
//import org.jboss.netty.channel.*;
//import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
//import org.jboss.netty.handler.codec.serialization.ClassResolvers;
//import org.jboss.netty.handler.codec.serialization.ObjectDecoder;
//
//import java.net.InetSocketAddress;
//import java.nio.charset.Charset;
//import java.util.concurrent.Executors;
//
///**
// * User: zhenbao.zhou
// * Date: 4/17/15
// * Time: 3:43 PM
// */
//public class HelloServer {
//
//    public static void main(String args[]) {
//        // Server服务启动器
//        ServerBootstrap bootstrap = new ServerBootstrap(
//          new NioServerSocketChannelFactory(
//            Executors.newCachedThreadPool(),
//            Executors.newCachedThreadPool()));
//        // 设置一个处理客户端消息和各种消息事件的类(Handler)
//        bootstrap
//          .setPipelineFactory(new ChannelPipelineFactory() {
//
//            /*  @Override
//              public ChannelPipeline getPipeline()
//                throws Exception {
//                  return Channels
//                    .pipeline(new HelloServerHandler());
//              }
//              */
//
//              @Override
//              public ChannelPipeline getPipeline() throws Exception {
//                  return Channels.pipeline(
//                    new ObjectDecoder(10, ClassResolvers.cacheDisabled(Command.class.getClassLoader()).getClass().getClassLoader()),
//                    new
//                      HelloServerHandler());
//                   //  new HelloServerHandler());
//              }
//
//          });
//        // 开放8000端口供客户端访问。
//        bootstrap.bind(new InetSocketAddress(8001));
//    }
//
//    private static class HelloServerHandler extends
//      SimpleChannelHandler {
//
//        /**
//         * 当有客户端绑定到服务端的时候触发，打印"Hello world, I'm server."
//         *
//         * @alia OneCoder
//         * @author lihzh
//         */
//        /*
//        @Override
//        public void channelConnected(
//          ChannelHandlerContext ctx,
//          ChannelStateEvent e) {
//            System.out.println("Hello world, I'm server.");
//        }
//        */
//
//        @Override
//        public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
//            Command command = (Command)e.getMessage();
//            System.out.println("Server command:" + command.getActionName());
//
//            /*
//            ChannelBuffer buffer = (ChannelBuffer)e.getMessage();
//            System.out.println("Receive:"+buffer.toString(Charset.defaultCharset()));
//            String msg = buffer.toString(Charset.defaultCharset()) + "has been processed!";
//            ChannelBuffer buffer2 = ChannelBuffers.buffer(msg.length());
//            buffer2.writeBytes(msg.getBytes());
//            e.getChannel().write(buffer2);
//            */
//        }
//    }
//}
