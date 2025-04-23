package org.example.marketing.dto.keyword

object Prompts {
    const val BASE_PROMPT = """
Generate 10 unique keyword combinations in Korean based on the keyword and the user context.


Rules:
- Each combination must contain at least 2 and at most 3 keyword components. 
- 60% of the combinations (i.e., 6 out of 10) must contain exactly 2 components only (e.g., City + Food type or City + Event).
- Each combination must be related with user-input keyword and context as possible as
- Required: City, District (Districts can include nearby areas mentioned or implied).
- Optional: Only one of the following can be included per combination: Food type or dish, or Event (not both).
- Do not allow combinations like: City + District only.
- Allow combinations like: City + Food type.

- Use the user context to better understand tone, intent, or occasion.
- Avoid duplicates and make all combinations diverse and realistic.
- Return only the keyword combinations, one per line, in Korean.
"""
}