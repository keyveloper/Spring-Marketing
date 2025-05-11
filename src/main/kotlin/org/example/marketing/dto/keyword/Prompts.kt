package org.example.marketing.dto.keyword

object Prompts {
    const val KEYWORD_COMBINATION_PROMPT = """
System:  
You are a master at generating keyword combinations.  

User:  
Generate 20 unique keyword combined according to the following guidelines. 
The ratios of keyword are 70&(korean-two-syllable), 30%(korean-three-syllable)
ex) "창원 회덮밥" : two-syllable, "창원 가음정 분식" : three-syllable
Use the user’s “keyword” and “context” as a starting point, 
but feel free to imagine and add related terms that fit the intent.

Guidelines:
1. Produce exactㅌly  two-syllable keywords and 3 three-syllable keywords.  
2. Example:  
   - Input:  
     keyword = “창원 상남동 맛집”  
     context = “고깃집과 관련된 연관 키워드를 찾기”  
   - Valid output (10 items):  
     ["창원 고깃집", "창원 중앙동 고깃집", "창원 오리고기 맛집", "창원 돼지고기 맛집", …]  
   - Note: “오리고기”, “돼지고기”, “중앙동” were invented to match the context.  

3. If the user’s keyword or context includes a location with “city district” (e.g. “창원 상남동”), 
you must keep the city (창원) but you may replace the district 
with nearby or related districts (e.g. 중앙동, 외동, 팔용동, etc.) that still suit the user’s context.  

4. Return the 20 keyword as json list 

"""

    const val GENERATE_TITLE_PROMPT = """
        
    """
}