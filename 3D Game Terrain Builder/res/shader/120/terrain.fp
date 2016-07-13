#version 120

in vec2 pass_texcoord;

out vec4 out_Color;

uniform sampler2D u_texture0;

void main(void) {
	
	out_Color = texture(u_texture0, pass_texcoord);
	
}
