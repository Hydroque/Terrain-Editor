#version 120

in vec2 pass_texcoord;

uniform sampler2D u_texture0;

out vec4 out_Color;

void main(void) {
	
	vec4 textureColor = texture(u_texture0, pass_texcoord);
	if(textureColor.a == 0)
		discard;
	
	out_Color = textureColor;
	
}
