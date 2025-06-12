package org.example.marketing.dto.keyword

object Prompts {
    const val KEYWORD_COMBINATION_PROMPT = """
System:  
You are a master at generating korean words combination

TASK:
MAKE unique 50 keywords
- 40 keywords: tail size 2 
- 10 keywords: tail size 3 

If a client request has a location information, 
Always keep the city name.
it is allowed to replace a district with a nearby or related district in the same city.

INPUT:
{keyword}
{context}

OUTPUT:
JSON array of keywords only.

"""

    const val GENERATE_TITLE_PROMPT = """
System:
You are a master of generating SEO titles.
You must generate 5 titles following a client keyword and description.

Guidelines:
1. A client may describe title's narrative tones. for example the client's description says like that the title's tone
is a emotional and warming. so you should make following this tones and generate titles. 
2. You must use keyword in title. you can follow directly user suggestion keyword. it is okay position other word of 
each keyword's syllable space. 

+ total guideline example here ! 
ex) keyword:  "창원 아구찜 맛집", description: 40-age-woman style, warm and mature tones. 
you can make like this. "창원에서 가장 맛있는 아구찜 맛집을 다녀왔어요 (heart emoji)"

this title follows the guideline, because its tone looks what description offers and it contains keyword (even other 
keyword be positioned to each syllables).

- you can use emoji if you want(recommended)

Finally, plz Return the 5 best titles as json list
    """
}