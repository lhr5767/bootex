package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class OrderItems {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;
    private int count; //주문 수량

    //생성 메서드//
    public static OrderItems createOrderItems(Item item, int orderPrice, int count) {
        OrderItems orderItems = new OrderItems();
        orderItems.setItem(item);
        orderItems.setOrderPrice(orderPrice);
        orderItems.setCount(count);

        item.removeStock(count);
        return orderItems;
    }

    //비즈니스 로직//

    public void cancel() {
        getItem().addStock(count);
    }

    //조회 로직//
    //주문상품 전체 가격조회//
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
