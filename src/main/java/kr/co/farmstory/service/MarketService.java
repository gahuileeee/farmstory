package kr.co.farmstory.service;

import kr.co.farmstory.dto.CartsDTO;
import kr.co.farmstory.dto.CategoriesDTO;
import kr.co.farmstory.dto.PointsDTO;
import kr.co.farmstory.dto.ProductsDTO;
import kr.co.farmstory.entity.Carts;
import kr.co.farmstory.entity.Products;
import kr.co.farmstory.repository.CartsRepository;
import kr.co.farmstory.repository.CategoriesRepository;
import kr.co.farmstory.repository.ProductsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
                log.info(product.getCateNo() +"!!");
                log.info(categoriesRepository.findById(1)+"!!");
                lists.add( modelMapper.map(categoriesRepository.findById(product.getCateNo()), CategoriesDTO.class));
            }
            return lists;
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

    //결제창에서 상품들 조회
    public List<ProductsDTO> selectProductsByProdNum(List<Integer> prodNo){
        List<ProductsDTO> lists = new ArrayList<>();
        for(int no : prodNo){
            Products product = productsRepository.findById(no).get();
            lists.add(modelMapper.map(product, ProductsDTO.class));
        }
        return lists;
    }
}
