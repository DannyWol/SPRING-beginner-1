package org.danny.ex2.repository;

import org.danny.ex2.Memo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.lang.reflect.Member;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MemoRepositoryTest {

    @Autowired
    MemoRepository memoRepository;

    @Test
    public void 유저생성(){
        IntStream.rangeClosed(1,100).forEach(i->{
            Memo memo = Memo.builder()
                    .memoText("Hello"+i)
                    .build();
            memoRepository.save(memo);
        });
    }

    @Test
    public void 유저검색(){
        Optional<Memo> selectTemp = memoRepository.findById(1L);
        if(selectTemp.isPresent()){
            Memo memo = selectTemp.get();
            System.out.println(memo);
        }
    }

    @Test
    public void testUpdate(){
        Memo memo = Memo.builder()
                .mno(100L)
                .memoText("Jello")
                .build();
        memoRepository.save(memo);
    }

    @Test
    public void 유저삭제(){
        memoRepository.deleteById(100L);
    }

    @Test
    public void 페이징(){
        Pageable pageable = PageRequest.of(0, 10);
        Page<Memo> result = memoRepository.findAll(pageable);
        System.out.println(result);
    }

    @Test
    public void 테스트페이징(){
        Pageable pageable = PageRequest.of(1, 10);
        Page<Memo> result = memoRepository.findAll(pageable);

        System.out.println(result.getTotalPages());
        System.out.println(result.getTotalElements());
        System.out.println(result.getNumber());
        System.out.println(result.getSize());
        System.out.println(result.hasNext());
        System.out.println(result.isFirst());
        System.out.println(result.getContent());
    }

    @Test
    public void 페이징정렬(){
        Sort sort = Sort.by("mno").descending();
        Pageable pageable = PageRequest.of(0, 10, sort);
        Page<Memo> result = memoRepository.findAll(pageable);

        result.forEach(i->{
            System.out.println(i);
        });
    }

    @Test
    public void 정렬조건2_페이징(){
        Sort sort1 = Sort.by("mno").descending();
        Sort sort2 = Sort.by("memoText").ascending();
        Sort sortAll = sort1.and(sort2);

        Pageable pageable = PageRequest.of(0, 10, sortAll);

        Page<Memo> result = memoRepository.findAll(pageable);

        result.forEach(i->{
            System.out.println(i);
        });
    }

    @Test
    public void 테스크쿼리메소드(){
        List<Memo> list = memoRepository.findByMnoBetweenOrderByMnoDesc(70L, 80L);

        list.forEach(i->{
            System.out.println(i);
        });
    }

    @Test
    public void 테스크쿼리메소드2(){
        List<Memo> list = memoRepository.findByMnoBetweenOrderByMno(70L, 80L);

        list.forEach(i->{
            System.out.println(i);
        });
    }

    @Test
    public void 테스트쿼리메소드3(){
        Sort sort = Sort.by("mno").descending();
        Pageable pageable = PageRequest.of(0, 10, sort);
        Page<Memo> page = memoRepository.findByMnoBetween(10L, 20L, pageable);

        page.forEach(i->{
            System.out.println(i);
        });

    }

    @Transactional
    @Commit
    @Test
    public void 삭제쿼리메소드1(){
        memoRepository.deleteByMno(10L);
    }

    @Transactional
    @Commit
    @Test
    public void 삭제쿼리메소드2(){
        memoRepository.deleteByMnoLessThan(10L);
    }

    @Test
    public void 쿼리어노테이션(){
        List<Memo> result = memoRepository.getListDesc();

        result.forEach(System.out::println);
    }
    
    @Test
    public void 쿼리어노테이션2(){
        memoRepository.updateMemoText(11L, "11번이다");
    }

    @Test
    public void 쿼리어노테이션3(){
        Memo memo = Memo.builder()
                .mno(11L)
                .memoText("Test")
                .build();

        memoRepository.updateMemo(memo);
    }

    @Test
    public void 쿼리어노테이션4(){
        Pageable pageable = PageRequest.of(0, 10);
        Page<Memo> listWithQuery = memoRepository.getListWithQuery(11L, pageable);
        listWithQuery.forEach(System.out::println);
    }

    @Test
    public void 쿼리어노테이션5(){
        Pageable pageable = PageRequest.of(0, 10);

        Page<Memo> listWithQuery = memoRepository.getListWithQuery(15L, pageable);

        listWithQuery.forEach(System.out::println);
    }

    @Test
    public void 네이티브쿼리어노테이션(){
        memoRepository.getNativeResult();
    }
}