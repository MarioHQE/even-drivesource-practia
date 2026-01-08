package com.example.oauhprueba.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@DynamicInsert
@DynamicUpdate
public class Usuario  {
    @Id
    @GeneratedValue( strategy = GenerationType.UUID)
    public  String id;

    @Column(name = "nombre")
    public String nombre;
    @Column(name = "contrasena")
    public String contraseña;


}
