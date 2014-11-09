package com.company.demo.beans


import org.apache.log4j.Logger
import com.company.demo.*
import org.primefaces.model.LazyDataModel
import org.primefaces.model.SortOrder

public class LazyAnagraphicDataModel extends LazyDataModel<Anagraphic> {
    Logger log = Logger.getLogger(LazyAnagraphicDataModel.class)
            
    AnagraphicService anagraphicService
    
    public LazyAnagraphicDataModel(AnagraphicService anagraphicService) {
        this.anagraphicService = anagraphicService
    }
    
    @Override
    public List<Anagraphic> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) { 
        log.info("first = " + first + ", pageSize = " + pageSize + ", sortField = " + sortField + ", sortOrder = " + sortOrder)
        def anagraphics
        if (!sortField) {
            anagraphics = anagraphicService.list(pageSize, first)
        } else {
            String order = sortOrder == SortOrder.ASCENDING ? "asc" : "desc"
            anagraphics = anagraphicService.list(pageSize, first, sortField, order)
        }
        
        
        List<Anagraphic> data = anagraphics.list

        //rowCount
        int dataSize = anagraphics.totalCount;
        this.setRowCount(dataSize);
 
        //paginate
        if(dataSize > pageSize) {
            try {
                return data.subList(first, first + pageSize);
            }
            catch(IndexOutOfBoundsException e) {
                return data.subList(first, first + (dataSize % pageSize));
            }
        }
        else {
            return data;
        }
    }
}
