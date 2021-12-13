package jpa.jpabook2.service;

import jpa.jpabook2.domain.Delivery;
import jpa.jpabook2.domain.Item.Item;
import jpa.jpabook2.domain.Member;
import jpa.jpabook2.domain.Order;
import jpa.jpabook2.domain.OrderItem;
import jpa.jpabook2.repository.ItemRepository;
import jpa.jpabook2.repository.MemberRepository;
import jpa.jpabook2.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    //주문
    @Transactional
    public Long order(Long memberId,Long itemId,int count){
        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.createorderItem(item,item.getPrice(),count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);
        return order.getId();
    }
    //취소
    @Transactional
    public void cancelOrder(Long orderId){

    }
    //조회
}
