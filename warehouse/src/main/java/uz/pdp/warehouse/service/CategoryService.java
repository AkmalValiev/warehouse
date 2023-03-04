package uz.pdp.warehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.warehouse.entity.Category;
import uz.pdp.warehouse.payload.Result;
import uz.pdp.warehouse.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> getCategories(){
        return categoryRepository.findAll();
    }

    public Category getCategory(Integer id){

        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()){
            return optionalCategory.get();
        }
        return new Category();
    }

    public Result addCategory(Category category){
        if (!category.getName().isEmpty()){
            boolean exists = categoryRepository.existsByName(category.getName());
            if (!exists){
                Category category1 = new Category();
                category1.setParentCategory(category.getParentCategory());
                category1.setName(category.getName());
                categoryRepository.save(category1);
                return new Result("Category qo'shildi", true);
            }
            return new Result("Bunday nomli category mavjud!", false);
        }
        return new Result("Category nomini kiriting!", false);
    }

    public Result editCategory(Integer id, Category category){
        if (!category.getName().isEmpty() && id!=null){
            Optional<Category> optionalCategory = categoryRepository.findById(id);
            if (optionalCategory.isPresent()){
                Category category1 = optionalCategory.get();
                if (category1.getName().equals(category.getName())){
                    category1.setParentCategory(category.getParentCategory());
                    category1.setName(category.getName());
                    categoryRepository.save(category1);
                    return new Result("Category taxrirlandi!", true);
                }else {
                    boolean exists = categoryRepository.existsByName(category.getName());
                    if (!exists){
                        category1.setParentCategory(category.getParentCategory());
                        category1.setName(category.getName());
                        categoryRepository.save(category1);
                        return new Result("Category taxrirlandi!", true);
                    }else {
                        return new Result("Bunday nomli category mavjud!", false);
                    }
                }

            }
            return new Result("Kiritilgan id bo'yicha category topilmadi!", false);
        }
        return new Result("Qatorlarni to'ldiring!", false);
    }

    public Result deleteCategory(Integer id){
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()){
            try {
                categoryRepository.delete(optionalCategory.get());
                return new Result("Category o'chirildi!", true);
            }catch (Exception e){
                return new Result("Categoryga tegishli boshqa maxsulotlar bo'lganligi sababli o'chirishning imkoni bo'lmadi!", false);
            }
        }
        return new Result("Kiritilgan id bo'yicha category topilmadi!",false);
    }

}
