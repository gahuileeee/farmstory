package kr.co.farmstory.service;

import kr.co.farmstory.dto.*;
import kr.co.farmstory.entity.*;
import kr.co.farmstory.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class MarketService {
    private ProductsRepository productsRepository;
    private CategoriesRepository categoriesRepository;
    private CartsRepository cartsRepository;
    private UserRepository userRepository;
    private PointsRepository pointsRepository;
    private OrdersRepository ordersRepository;
    private OrdersItemsRepository ordersItemsRepository;
    private ModelMapper modelMapper;

    //상품들 조회
    public List<ProductsDTO> selectProducts(){
        log.info(productsRepository.findAll().toString());
        return productsRepository.findAll().stream().map(entity->modelMapper.map(entity, ProductsDTO.class)
        ).toList();
    }

    //상품 하나 조회
    public ProductsDTO selectProduct(int prodNum){
        return modelMapper.map(productsRepository.findById(prodNum) , ProductsDTO.class);
    }


    //카테고리들 조회 (프로덕트 이용하여)
    public List<CategoriesDTO> selectCategoriesByProduct( List<ProductsDTO> products ){
        List<CategoriesDTO> lists =new ArrayList<>();
            for(ProductsDTO product : products){
                lists.add( modelMapper.map(categoriesRepository.findById(product.getCateNo()), CategoriesDTO.class));
            }
            return lists;
    }

    //카테고리 하나 조회
    public CategoriesDTO selectCategoryByProduct(ProductsDTO productsDTO){
     return modelMapper.map(categoriesRepository.findById(productsDTO.getCateNo()),CategoriesDTO.class);
    }

    //장바구니 넣기
    public ResponseEntity insertCarts(CartsDTO cartsDTO){
        Map<String , String> map = new HashMap<>();
         try{cartsRepository.save(modelMapper.map(cartsDTO, Carts.class));
             map.put("result" ,"success");
        }catch (Exception e){
             map.put("result" ,"fail");
        }
         return ResponseEntity.ok().body(map);
    }

    //장바구니 조회
    public  List<CartsDTO> selectCartsByUser(String userId){
        return cartsRepository.findAllByUserId(userId).stream()
                .map(entity -> modelMapper.map(entity, CartsDTO.class)).toList();
    }

    //장바구니 이용한 상품들 조회
    public List<ProductsDTO> selectProductsByCart(List<CartsDTO> cartsDTOS){
        List<ProductsDTO> lists = new ArrayList<>();
        for(CartsDTO cart : cartsDTOS){
            Products product = productsRepository.findById(cart.getProdNo()).get();
            lists.add(modelMapper.map(product, ProductsDTO.class));
        }
        return lists;
    }

    //장바구니에서 삭제
    public ResponseEntity deleteCarts(List<Integer> cateNos){
        Map<String , String> map = new HashMap<>();
        log.info(cateNos.toString()+"here!");
        log.info(cateNos.get(0).toString()+"here2!");
        int a = cateNos.get(0) +10;
        log.info(a+"!!");
        for (int cateNo : cateNos){
            cartsRepository.deleteById(cateNo);
            map.put("result" ,"success");
        }

        return ResponseEntity.ok().body(map);
    }

    //결제창에서 장바구니 조회
    public  List<CartsDTO> selectCartsByCartNo(List<Integer> lists){
        List<CartsDTO> cartsList = new ArrayList<>();
        for(int a : lists){
            cartsList.add(modelMapper.map(cartsRepository.findById(a) , CartsDTO.class));
        }
        return  cartsList;
    }
    //사용자 조회
    public UserDTO selectUserByUid (String uid){
        return modelMapper.map(userRepository.findById(uid), UserDTO.class);
    }
    //포인트 조회
    public List<PointsDTO> selectPointsByUid(String uid){
        return pointsRepository.findAllByUserId(uid).stream().map(entity ->
                modelMapper.map(entity, PointsDTO.class)).toList();
    }


    //주문 넣기
    @Transient
    public int insertOrers(OrdersDTO ordersDTO){
        Orders ordersDTO1 = ordersRepository.save(modelMapper.map(ordersDTO, Orders.class));
        return ordersDTO1.getOrderNo();
    }

    //orderItems넣기
    public void insertOrderitems(List<Integer> prods , List<Integer> counts, int orderNo){
        for(int i=0 ; i<prods.size(); i++){
            Products products = productsRepository.findById(prods.get(i)).get();
            OrderItems orderItems = OrderItems
                    .builder()
                    .orderNo(orderNo)
                    .prodNo(prods.get(i))
                    .itemPrice(products.getProdPrice())
                    .itemCount(counts.get(i))
                    .build();

            ordersItemsRepository.save(orderItems);
            //재고량 빼기
            ProductsDTO productsDTO = modelMapper.map(products, ProductsDTO.class);
            productsDTO.setProdStock(productsDTO.getProdStock() - counts.get(i));
            productsRepository.save(modelMapper.map(productsDTO, Products.class));
        }
    }

    //point 빼기 (points 목록은 안 할래..)
    public  void updatePoint(String uid, int point){
        UserDTO userDTO = modelMapper.map(userRepository.findById(uid), UserDTO.class);
        userDTO.setTotalPoint(userDTO.getTotalPoint()- point);
        userRepository.save(modelMapper.map(userDTO, User.class));
    }
}
