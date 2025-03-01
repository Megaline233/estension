package xyz.Extencion.extencion.utils;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class Particle {
    private float x;
    private float y;
    private float speed;
    private float angle;
}