-- for openai
drop table public.vector_store;
CREATE TABLE public.vector_store (
                                     id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
                                     content text,
                                     metadata json,
                                     embedding public.vector(1536)
);
ALTER TABLE public.vector_store OWNER TO myuser;


-- for ollama embedding model all-minlm
drop table public.vector_store;

CREATE TABLE IF NOT EXISTS public.vector_store
(
    id        uuid default uuid_generate_v4() not null
    primary key,
    content   text,
    metadata  json,
    embedding vector(384)
    );
ALTER TABLE public.vector_store OWNER TO myuser;