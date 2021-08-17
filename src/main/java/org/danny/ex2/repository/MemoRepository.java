package org.danny.ex2.repository;

import org.danny.ex2.Memo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface MemoRepository extends JpaRepository<Memo, Long> {

    List<Memo> findByMnoBetweenOrderByMnoDesc(Long from, Long to);

    List<Memo> findByMnoBetweenOrderByMno(Long from, Long to);

    Page<Memo> findByMnoBetween(Long from, Long to, Pageable pageable);

    void deleteByMno(Long mno);

    void deleteByMnoLessThan(Long mno);

    @Query("SELECT m from Memo m order by m.mno desc ")
    List<Memo> getListDesc();

    @Transactional
    @Modifying
    @Query("UPDATE Memo m SET m.memoText = :memoText where m.mno = :mno")
    int updateMemoText(@Param("mno") Long mno, @Param("memoText") String memoText);

    @Transactional
    @Modifying
    @Query("UPDATE Memo m SET m.memoText = :#{#param.memoText} WHERE m.mno = :#{#param.mno}")
    int updateMemo(@Param("param") Memo memo);

    @Query(value = "SELECT m from Memo m WHERE m.mno > :mno",
            countQuery = "SELECT COUNT(m) FROM Memo m WHERE m.mno > :mno")
    Page<Memo> getListWithQuery(Long mno, Pageable pageable);

    @Query(value = "SELECT m.mno, m.memoText, CURRENT_DATE FROM Memo m WHERE m.mno > :mno",
            countQuery = "SELECT COUNT(m) FROM Memo m WHERE m.mno > :mno")
    Page<Object[]> getListWithQueryObject(Long mno, Pageable pageable);

    @Query(value = "SELECT * FROM tbl_memo WHERE mno > 0", nativeQuery = true)
    List<Object[]> getNativeResult();
}
