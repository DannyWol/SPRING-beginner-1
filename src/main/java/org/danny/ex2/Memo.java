package org.danny.ex2;

import lombok.*;

import javax.persistence.*;


@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_memo")
@Entity
public class Memo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;

    @Column(length = 200, nullable = false)
    private String memoText;
}
