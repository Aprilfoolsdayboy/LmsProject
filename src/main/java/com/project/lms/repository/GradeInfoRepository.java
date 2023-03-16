package com.project.lms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.lms.entity.GradeInfoEntity;
import com.project.lms.entity.TestInfoEntity;
import com.project.lms.entity.member.MemberInfoEntity;
import com.project.lms.entity.member.TeacherInfo;
import com.project.lms.vo.ScoreAvgListBySubjectVO;
import com.project.lms.vo.request.ScoreListBySubjectYearVO;

public interface GradeInfoRepository extends JpaRepository<GradeInfoEntity, Long> {
    List<GradeInfoEntity> findByStudent(MemberInfoEntity student);

    GradeInfoEntity findByTest(TestInfoEntity test);

    // @Query(value =
    // "select ti.testName as testName, si.subName as subjectName, gi.student.miSeq as studentNum, gi.grade as grade from GradeInfoEntity gi "
    // + "join TestInfoEntity ti on gi.test.testSeq = ti.testSeq "
    // + "join SubjectInfoEntity si on gi.subject.subSeq = si.subSeq "
    // + "where gi.student.miSeq =:seq and function('date_format',ti.testDate ,'%Y' ) = function('date_format', now(),'%Y')"
    // )
    // List<ScoreListBySubjectYearVO> findByYearScoreList(@Param("seq") Long seq);

    @Query(value =
    "SELECT ti.testName as testName, "
    +
    "GROUP_CONCAT(DISTINCT CASE WHEN si.subName = '독해' THEN gi.grade END) as comprehension, "
    +
    "GROUP_CONCAT(DISTINCT CASE WHEN si.subName = '어휘' THEN gi.grade END) as vocabulary, "
    +
    "GROUP_CONCAT(DISTINCT CASE WHEN si.subName = '문법' THEN gi.grade END) as grammer, "
    +
    "GROUP_CONCAT(DISTINCT CASE WHEN si.subName = '듣기' THEN gi.grade END) as listening "
    +
    "FROM GradeInfoEntity gi JOIN TestInfoEntity ti ON gi.test.testSeq = ti.testSeq "
    +
    "JOIN SubjectInfoEntity si ON gi.subject.subSeq = si.subSeq "
    +
    "WHERE gi.student.miSeq =:seq AND FUNCTION('date_format',ti.testDate ,'%Y' ) = FUNCTION('date_format', now(),'%Y') GROUP BY ti.testSeq")
    List<ScoreListBySubjectYearVO> findByYearScoreList(@Param("seq") Long seq);


    @Query("SELECT si.miSeq FROM ClassStudentEntity cst join cst.classInfo ci join cst.student si WHERE ci.ciSeq = :classSeq")
    List<Long> findByCsSeq(@Param("classSeq") Long classSeq); // 조회하려는 반의 학생 시퀀스를 모두 리스트에 담는다.

    @Query("SELECT sub.subName AS subject, AVG(grd.grade) as avg FROM GradeInfoEntity grd "
            + "JOIN SubjectInfoEntity sub ON grd.subject.subSeq = sub.subSeq "
            + "JOIN TestInfoEntity tt ON tt.testSeq = grd.test.testSeq "
            + "WHERE DATE_FORMAT(tt.testDate, '%Y%m') = :yearMonth AND grd.student.miSeq IN :seqs " 
            + "GROUP by grd.subject.subSeq")
    List<ScoreAvgListBySubjectVO> avgBySubject(@Param("seqs")List<Long> list, @Param("yearMonth") Integer yearMonth); // 과목별 평균을 찾아 리스트에 담는다.
    
}
