package com.edplan.framework.media.video.tbv;
import org.json.JSONObject;
import com.edplan.framework.media.video.tbv.decode.TBVInputStream;
import java.io.IOException;
import org.json.JSONException;
import com.edplan.framework.media.video.tbv.encode.TBVOutputStream;

/**
 *一种渲染材质组成的视频，将所有信息存在文件里然后播放
 *
 *文件格式：.tbv
 *
 *数据格式(v0.0.0)：
 * String：长度(int)+长度个byte组成的string，编码：UTF-8
 * Json：就是一个String
 * Color255：一个int表示的颜色
 * Boolean：一个usign byte，0为false，其余为true
 * BlendType(int)：渲染模式
 * BaseVertex：基础顶点，x(float)+y(float)+z(float)+顶点颜色(Color255)
 * TextureVertex：描述材质的顶点，BaseVertex+textureX(float)+textureY(float)
 * RenderData：描述绘制的顶点数据，按三角形存储
 * BaseTexture：纹理id(int)+RenderData
 *
 *HEADER：
 * String：固定为TextureBasedVideo，用来判断文件种类
 * Json：基础数据用Json来表示
 * xxxx：保存纹理映射
 *
 *BODY：
 * BODY由许多帧数据组成
 *	每一帧包含帧头数据以及许多事件，以帧结束事件结束
 *
 *  帧头数据：
 *    本帧的数据长度(int)
 *    为0表示跳过
 *
 *  事件种类：
 *    帧结束（-1）
 *    #绘制操作
 *    绘制材质（0）
 *    绘制色块（1）
 *    #end 绘制操作
 *    
 *    #util
 *    切换设置（1000）
 *    #end util
 *
 *  基础格式：事件种类(int)+事件对应的数据
 *
 *  详细：
 *    绘制材质：跟一个BaseTexture数据，并绘制
 *    绘制色块：无材质的绘制
 *    切换设置：设置种类(int)+具体数据(长为64byte，可能有没有使用的位)
 *    帧结束：无对应数据
 *
 */


public class TextureBasedVideo
{
	public static final int VERSION=0;
	public static final String HEADER_TYPE="TextureBasedVideo";
	
	
	
	public static class Header{
		public String type;
		public JSONObject jsonData;
		public TextureNode[] textures;
		
		public static Header read(TBVInputStream in) throws IOException, TBVException, JSONException{
			Header h=new Header();
			h.type=in.readString();
			if(!HEADER_TYPE.equals(h.type)){
				throw new TBVException("err type: "+h.type);
			}
			h.jsonData=in.readJSONObject();
			return h;
		}
		
		public static void write(TBVOutputStream out,Header h) throws TBVException, IOException{
			if(!HEADER_TYPE.equals(h.type)){
				throw new TBVException("err type: "+h.type);
			}
			out.writeString(h.type);
			out.writeJSONObject(h.jsonData);
		}
	}
}
