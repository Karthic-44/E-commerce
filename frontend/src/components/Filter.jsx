import { Button, FormControl, InputLabel, MenuItem, Select, Tooltip } from "@mui/material";
import { useState, useEffect } from "react";
import { FiArrowDown, FiArrowUp, FiRefreshCw, FiSearch } from "react-icons/fi";


const Filter = () => {
    const categories = [{categoryId: 1, categoryName: "Electronics"},
                        {categoryId: 2, categoryName: "Books"},
                        {categoryId: 3, categoryName: "Clothing"},
                        {categoryId: 4, categoryName: "Toys"},
                        {categoryId: 5, categoryName: "Furniture"},
    ];

const [category, setCategory] = useState("all");
const handleCategoryChange = (event) => {
    setCategory(event.target.value);
    };

    return(
        <div className="flex lg:flex-row flex-col-reverse lg:justify-between justify-center items-center gap-4 ">
           {/* SEARCH BAR */} 
           <div className="relative flex items-center 2xl:w-[450px] sm:w-[420px] w-full">
                <input 
                    type="text"
                    placeholder="Search Products"
                    className="border border-gray-400 text-slate-800 rounded-md py-2 pl-10 pr-4 w-full focus:outline-none focus:ring-2 focus:ring-[#1976d2]"/>
                <FiSearch className="absolute left-3 text-slate-800 size={20}"/>
           </div>

            {/* Category Selector */}
             <div className="flex sm:flex-row flex-col gap-4 items-center">
              <FormControl
                    className="text-slate-800 border-slate-700"
                    variant="outlined"
                    size="small">
                        <InputLabel id="category-select-label">Category</InputLabel>
                        <Select
                            labelId="category-select-label"
                            value={category}
                            onChange={handleCategoryChange}
                            label="Category"
                            className="min-w-[120px] text-slate-800 border-slate-700"
                         >
                           
                           <MenuItem value="all">All</MenuItem>
                            
                            {categories.map((item) => (
                                <MenuItem key={item.categoryId} value={item.categoryName}>
                                    {item.categoryName}
                                </MenuItem>
                          
                            ))}
                         
                         </Select>
                </FormControl>

             </div>
        </div>
    )
}

export default Filter;