package com.socialcommerce.feed;

import com.socialcommerce.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;


@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "activity_type", discriminatorType = DiscriminatorType.STRING)
@Entity
public abstract class Activity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_id")
    private Long id;

    @OneToMany(mappedBy = "activity")
    private List<Feed> feeds;

    public abstract ActivityType getActivityType();
}
