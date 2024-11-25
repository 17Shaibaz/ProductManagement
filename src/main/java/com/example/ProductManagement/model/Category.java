package com.example.ProductManagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    //    @DateTimeFormat(pattern = "dd/mm/yyyy")
    private Date createdAt;
    private Date updatedAt;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category" ,fetch=FetchType.LAZY)
    private List<Product> products;

}
