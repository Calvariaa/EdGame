precision mediump float;

varying vec3 f_Position;
varying vec4 f_VaryingColor;
varying vec3 f_MaskPosition;

uniform float u_FinalAlpha;
uniform vec4 u_MixColor;

void main(){
	vec4 c=u_MixColor*f_VaryingColor;
	c*=u_FinalAlpha;
	if(c.a<0.001)discard;
	gl_FragColor=c;
}
