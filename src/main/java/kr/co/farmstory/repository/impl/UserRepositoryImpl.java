package kr.co.farmstory.repository.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.farmstory.dto.UserPageRequestDTO;
import kr.co.farmstory.entity.QOrders;
import kr.co.farmstory.entity.QUser;
import kr.co.farmstory.repository.custum.UserRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private QUser qUser = QUser.user;
    private QOrders qOrders = QOrders.orders;

    @Override
    public Page<Tuple> selectsUsers(UserPageRequestDTO userPageRequestDTO, Pageable pageable) {

        String sort = userPageRequestDTO.getSort();

        OrderSpecifier<String> orderSpecifier = null;

        log.info("here1 : " + sort);


        if(sort != null && sort.startsWith("uid-")){
            log.info("here2");
            orderSpecifier = sort.equals("uid-desc") ? qUser.uid.desc() :  qUser.uid.asc();
        }else if(sort != null && sort.startsWith("name-")){
            log.info("here3");
            orderSpecifier = sort.equals("name-desc") ? qUser.name.desc() :  qUser.name.asc();
        }else if(sort != null && sort.startsWith("email-")){
            log.info("here4");
            orderSpecifier = sort.equals("email-desc") ? qUser.email.desc() :  qUser.email.asc();
        }else if(sort != null && sort.startsWith("grade-")){
            log.info("here5");
            orderSpecifier = sort.equals("grade-desc") ? qUser.grade.desc() :  qUser.grade.asc();
        }else {
            log.info("here6");
            orderSpecifier = qUser.uid.asc();
        }

        log.info("here7");
        QueryResults<Tuple> results = jpaQueryFactory
                .select(qUser, qOrders.orderTotalPrice.sum().as("totalPrice"))
                .from(qUser)
                .leftJoin(qOrders)
                .on(qUser.uid.eq(qOrders.userId))
                .groupBy(qUser.uid)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orderSpecifier)
                .fetchResults();

        //log.info("selectUsers...1-2 : " + results);

        List<Tuple> content = results.getResults();

        //log.info("selectUsers...1-3 : " + content);

        long total = results.getTotal();
        //log.info("selectUsers....1-4:" + total);

        // 페이징 처리를 위해 page 객체 리턴
        return new PageImpl<>(content, pageable, total);

    }


}
