#version 120

in vec3 pass_position;
in vec2 pass_texcoord;
in vec3 pass_surfaceNormal;

in vec3 pass_lightVector;
in vec3 pass_cameraVector;

out vec4 out_Color;

uniform sampler2D u_texture0;

uniform vec3 light_color;
uniform float shineDamper, reflectivity;

void main(void) {
	
	vec3 normalizedNormal = normalize(pass_surfaceNormal);
	
	vec3 normalizedLightVector = normalize(pass_lightVector);
	vec3 normalizedCameraVector = normalize(pass_cameraVector);
	vec3 inversed_nCameraVector = -normalizedCameraVector;
	
	vec3 reflectedLight = reflect(inversed_nCameraVector, normalizedNormal);
	
	float diffusenDot = dot(normalizedNormal, normalizedLightVector);
	float damper = max(diffusenDot, 0.5);
	
	float specularnDot = max(dot(reflectedLight, normalizedCameraVector), 0.0);
	float specularDamper = pow(specularnDot, shineDamper);
	
	vec3 finalSpecular = specularDamper * reflectivity * light_color;
	vec3 finalDiffuse = damper * light_color;
	
	vec4 textureColor = texture(u_texture0, pass_texcoord);
	if(textureColor.a == 0)
		discard;
	
	out_Color = vec4(finalDiffuse, 1.0) * textureColor + vec4(finalSpecular, 1.0);
	
}
