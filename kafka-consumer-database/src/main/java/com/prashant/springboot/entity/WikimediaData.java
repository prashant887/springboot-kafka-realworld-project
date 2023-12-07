package com.prashant.springboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "wikimedia_changes")
@Getter
@Setter
public class WikimediaData {



        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        //Lob to store very long objects
        @Lob
        private String wikiEventData;

}
