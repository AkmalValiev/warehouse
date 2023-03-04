package uz.pdp.warehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.warehouse.entity.Attachment;
import uz.pdp.warehouse.entity.Category;
import uz.pdp.warehouse.entity.Measurement;
import uz.pdp.warehouse.entity.Product;
import uz.pdp.warehouse.payload.ProductDto;
import uz.pdp.warehouse.payload.Result;
import uz.pdp.warehouse.repository.AttachmentRepository;
import uz.pdp.warehouse.repository.CategoryRepository;
import uz.pdp.warehouse.repository.MeasurementRepository;
import uz.pdp.warehouse.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    MeasurementRepository measurementRepository;

    public List<Product> getProducts(){
        return productRepository.findAll();
    }

    public Product getProduct(Integer id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()){
            return optionalProduct.get();
        }
        return new Product();
    }

     public Result addProduct(ProductDto productDto){
        if (!productDto.getName().isEmpty() && productDto.getCategoryId()!=null && productDto.getAttachmentId()!=null && productDto.getMeasurementId()!=null){
            boolean exists = productRepository.existsByName(productDto.getName());
            if (!exists){
                Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
                if (optionalCategory.isPresent()){
                    Category category = optionalCategory.get();
                    Optional<Attachment> optionalAttachment = attachmentRepository.findById(productDto.getAttachmentId());
                    if (optionalAttachment.isPresent()){
                        Attachment attachment = optionalAttachment.get();
                        Optional<Measurement> optionalMeasurement = measurementRepository.findById(productDto.getMeasurementId());
                        if (optionalMeasurement.isPresent()){
                            int code = 0;
                            List<Product> productList = productRepository.findAll();
                            if (productList.isEmpty()){
                                code = code+1;
                            }else {
                                String code1 = productList.get(productList.size() - 1).getCode();
                                code = Integer.parseInt(code1)+1;
                            }
                            Measurement measurement = optionalMeasurement.get();
                            Product product = new Product();
                            product.setName(productDto.getName());
                            product.setCode(String.valueOf(code));
                            product.setFile(attachment);
                            product.setMeasurement(measurement);
                            product.setCategory(category);
                            product.setActive(product.isActive());
                            productRepository.save(product);
                            return new Result("Product qo'shildi!", true);

                        }
                        return new Result("Kiritilgan id bo'yicha o'lchov birligi topilmadi!", false);
                    }
                    return new Result("Kiritilgan id bo'yicha file topilmadi!", false);
                }
                return new Result("Kritilgan id bo'yicha category topilmadi!", false);
            }
            return new Result("Kiritilgan nomli product mavjud, boshqa nom bering!", false);
        }
        return new Result("Qatorlarni to'ldiring!", false);
    }

    public Result editProduct(Integer id, ProductDto productDto){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            if (!productDto.getName().isEmpty() && productDto.getCategoryId()!=null && productDto.getAttachmentId()!=null && productDto.getMeasurementId()!=null) {
                if (!productDto.getName().equals(product.getName())){
                    boolean exists = productRepository.existsByName(productDto.getName());
                if (!exists) {
                    Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
                    if (optionalCategory.isPresent()){
                        Category category = optionalCategory.get();
                        Optional<Attachment> optionalAttachment = attachmentRepository.findById(productDto.getAttachmentId());
                        if (optionalAttachment.isPresent()){
                            Attachment attachment = optionalAttachment.get();
                            Optional<Measurement> optionalMeasurement = measurementRepository.findById(productDto.getMeasurementId());
                            if (optionalMeasurement.isPresent()){
                                int code = 0;
                                List<Product> productList = productRepository.findAll();
                                if (productList.isEmpty()){
                                    code = code+1;
                                }else {
                                    String code1 = productList.get(productList.size() - 1).getCode();
                                    code = Integer.parseInt(code1)+1;
                                }
                                Measurement measurement = optionalMeasurement.get();
                                product.setName(productDto.getName());
                                product.setCode(String.valueOf(code));
                                product.setFile(attachment);
                                product.setMeasurement(measurement);
                                product.setCategory(category);
                                product.setActive(product.isActive());
                                productRepository.save(product);
                                return new Result("Product taxrirlandi!", true);

                            }
                            return new Result("Kiritilgan id bo'yicha o'lchov birligi topilmadi!", false);
                        }
                        return new Result("Kiritilgan id bo'yicha file topilmadi!", false);
                    }
                    return new Result("Kritilgan id bo'yicha category topilmadi!", false);
                }
                return new Result("Kiritilgan nomli product mavjud, boshqa nom bering!", false);
            }else {
                    Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
                    if (optionalCategory.isPresent()){
                        Category category = optionalCategory.get();
                        Optional<Attachment> optionalAttachment = attachmentRepository.findById(productDto.getAttachmentId());
                        if (optionalAttachment.isPresent()){
                            Attachment attachment = optionalAttachment.get();
                            Optional<Measurement> optionalMeasurement = measurementRepository.findById(productDto.getMeasurementId());
                            if (optionalMeasurement.isPresent()){
                                int code = 0;
                                List<Product> productList = productRepository.findAll();
                                if (productList.isEmpty()){
                                    code = code+1;
                                }else {
                                    String code1 = productList.get(productList.size() - 1).getCode();
                                    code = Integer.parseInt(code1)+1;
                                }
                                Measurement measurement = optionalMeasurement.get();
                                product.setName(productDto.getName());
                                product.setCode(String.valueOf(code));
                                product.setFile(attachment);
                                product.setMeasurement(measurement);
                                product.setCategory(category);
                                product.setActive(product.isActive());
                                productRepository.save(product);
                                return new Result("Product taxrirlandi!", true);

                            }
                            return new Result("Kiritilgan id bo'yicha o'lchov birligi topilmadi!", false);
                        }
                        return new Result("Kiritilgan id bo'yicha file topilmadi!", false);
                    }
                    return new Result("Kritilgan id bo'yicha category topilmadi!", false);
                }
            }
            return new Result("Qatorlarni to'ldiring!", false);
        }
        return new Result("Kiritilgan id bo'yicha product topilmadi!", false);
    }

    public Result deleteProduct(Integer id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()){
            try {
                productRepository.delete(optionalProduct.get());
                return new Result("Product o'chirildi!", true);
            }catch (Exception e){
                return new Result("Bu product boshqa joyda ishlatilganligi uchun o'chirish imkoniyati yo'q!", false);
            }

        }
        return new Result("Kiritilgan id bo'yicha product topilmadi!", false);
    }

}
