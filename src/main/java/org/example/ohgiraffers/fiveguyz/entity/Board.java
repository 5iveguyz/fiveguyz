package org.example.ohgiraffers.fiveguyz.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "Post_ID")
    private int Post_ID;

    private String Title;

    private String Content;

    private String filename;

    private String filepath;

}
