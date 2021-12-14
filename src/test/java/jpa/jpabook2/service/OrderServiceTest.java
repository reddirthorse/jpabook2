package jpa.jpabook2.service;

import jpa.jpabook2.domain.Address;
import jpa.jpabook2.domain.Item.Book;
import jpa.jpabook2.domain.Item.Item;
import jpa.jpabook2.domain.Member;
import jpa.jpabook2.domain.Order;
import jpa.jpabook2.domain.OrderStatus;
import jpa.jpabook2.exception.NotEnoughStockException;
import jpa.jpabook2.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class OrderServiceTest {
    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception{
        Member member = createMember();

        Book book = createBook("jpa", 10000, 10);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        Order getOrder = orderRepository.findOne(orderId);

        assertEquals( OrderStatus.ORDER,getOrder.getStatus(),"상품 주문시 상태는 order");
        assertEquals(1,getOrder.getOrderItems().size(),"주문한 상품 종류 수가 정확해야 한다.");
        assertEquals(10000*orderCount,getOrder.getTotalPrice(),"주문 가격은 가격*수량이다");
        assertEquals(8,book.getStockQuantity(),"주문수량만큼 재고가 줄어야 한다.");


    }



    @Test
    public void 주문취소() throws Exception{
    Member member = createMember();
    Item item = createBook("사용 JPA",10000,10);

    int orderCount = 2;

    Long orderId = orderService.order(member.getId(), item.getId(),orderCount);

    orderService.cancelOrder(orderId);

    Order getOrder = orderRepository.findOne(orderId);

    assertEquals(OrderStatus.CANCEL,getOrder.getStatus(),"주문 취소 시 상태는 cancel이다.");
    assertEquals(10,item.getStockQuantity(),"주문이 취소된 주문은 그만큼 재고가 증가해야한다.");
    }

    @Test
//          (expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception{
        Member member = createMember();
        Item item = createBook("jpa", 10000, 10);

        int orderCount = 11;

        try{
            orderService.order(member.getId(), item.getId(), orderCount);
        }catch(NotEnoughStockException e){
            return;
        }

        fail("재고 수량 예외가 발생해야한다.");

    }


    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울","강가","123-123"));
        em.persist(member);
        return member;
    }

}