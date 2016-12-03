--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.2
-- Dumped by pg_dump version 9.5.2

-- Started on 2016-06-10 09:20:19

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

SET search_path = public, pg_catalog;

SET default_with_oids = false;

--
-- TOC entry 183 (class 1259 OID 21579)
-- Name: application; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE application (
    id integer NOT NULL,
    customer_id integer NOT NULL,
    receipt_time timestamp without time zone NOT NULL,
    lead_time timestamp without time zone,
    vehicle_type_id integer NOT NULL,
    weight numeric,
    number_of_pallets integer,
    driver_id integer,
    application_state integer NOT NULL,
    loading_address character varying(250) NOT NULL,
    unloading_address character varying(250)
);


--
-- TOC entry 182 (class 1259 OID 21577)
-- Name: application_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE application_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2185 (class 0 OID 0)
-- Dependencies: 182
-- Name: application_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE application_id_seq OWNED BY application.id;


--
-- TOC entry 192 (class 1259 OID 21632)
-- Name: category_license; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE category_license (
    id integer NOT NULL,
    category character varying(30) NOT NULL
);


--
-- TOC entry 191 (class 1259 OID 21630)
-- Name: category_license_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE category_license_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2186 (class 0 OID 0)
-- Dependencies: 191
-- Name: category_license_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE category_license_id_seq OWNED BY category_license.id;


--
-- TOC entry 188 (class 1259 OID 21612)
-- Name: credentials; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE credentials (
    id integer NOT NULL,
    login character varying(200) NOT NULL,
    password character varying(200) NOT NULL,
    role integer NOT NULL
);


--
-- TOC entry 187 (class 1259 OID 21610)
-- Name: credentials_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE credentials_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2187 (class 0 OID 0)
-- Dependencies: 187
-- Name: credentials_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE credentials_id_seq OWNED BY credentials.id;


--
-- TOC entry 186 (class 1259 OID 21605)
-- Name: customer; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE customer (
    id integer NOT NULL,
    last_name character varying(200) NOT NULL,
    first_name character varying(200)
);


--
-- TOC entry 185 (class 1259 OID 21599)
-- Name: driver; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE driver (
    id integer NOT NULL,
    last_name character varying(100) NOT NULL,
    first_name character varying(100) NOT NULL,
    state_free boolean DEFAULT true NOT NULL,
    lead_time timestamp without time zone
);


--
-- TOC entry 193 (class 1259 OID 21640)
-- Name: driver_2_category_license; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE driver_2_category_license (
    driver_id integer NOT NULL,
    category_license_id integer NOT NULL
);


--
-- TOC entry 184 (class 1259 OID 21588)
-- Name: vehicle; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE vehicle (
    id integer NOT NULL,
    model character varying(200) NOT NULL,
    state_after_freight boolean DEFAULT true NOT NULL,
    vehicle_type_id integer NOT NULL,
    max_weight numeric,
    number_of_pallets integer,
    license_plate character varying(50) NOT NULL
);


--
-- TOC entry 190 (class 1259 OID 21622)
-- Name: vehicle_type; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE vehicle_type (
    id integer NOT NULL,
    type character varying(200) NOT NULL,
    "exists" boolean NOT NULL
);


--
-- TOC entry 189 (class 1259 OID 21620)
-- Name: vehicle_type_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE vehicle_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2188 (class 0 OID 0)
-- Dependencies: 189
-- Name: vehicle_type_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE vehicle_type_id_seq OWNED BY vehicle_type.id;


--
-- TOC entry 2018 (class 2604 OID 21582)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY application ALTER COLUMN id SET DEFAULT nextval('application_id_seq'::regclass);


--
-- TOC entry 2023 (class 2604 OID 21635)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY category_license ALTER COLUMN id SET DEFAULT nextval('category_license_id_seq'::regclass);


--
-- TOC entry 2021 (class 2604 OID 21615)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY credentials ALTER COLUMN id SET DEFAULT nextval('credentials_id_seq'::regclass);


--
-- TOC entry 2022 (class 2604 OID 21625)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY vehicle_type ALTER COLUMN id SET DEFAULT nextval('vehicle_type_id_seq'::regclass);


--
-- TOC entry 2170 (class 0 OID 21579)
-- Dependencies: 183
-- Data for Name: application; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2189 (class 0 OID 0)
-- Dependencies: 182
-- Name: application_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('application_id_seq', 1, false);


--
-- TOC entry 2179 (class 0 OID 21632)
-- Dependencies: 192
-- Data for Name: category_license; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2190 (class 0 OID 0)
-- Dependencies: 191
-- Name: category_license_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('category_license_id_seq', 1, false);


--
-- TOC entry 2175 (class 0 OID 21612)
-- Dependencies: 188
-- Data for Name: credentials; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2191 (class 0 OID 0)
-- Dependencies: 187
-- Name: credentials_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('credentials_id_seq', 1, false);


--
-- TOC entry 2173 (class 0 OID 21605)
-- Dependencies: 186
-- Data for Name: customer; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2172 (class 0 OID 21599)
-- Dependencies: 185
-- Data for Name: driver; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2180 (class 0 OID 21640)
-- Dependencies: 193
-- Data for Name: driver_2_category_license; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2171 (class 0 OID 21588)
-- Dependencies: 184
-- Data for Name: vehicle; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2177 (class 0 OID 21622)
-- Dependencies: 190
-- Data for Name: vehicle_type; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2192 (class 0 OID 0)
-- Dependencies: 189
-- Name: vehicle_type_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('vehicle_type_id_seq', 1, false);


--
-- TOC entry 2025 (class 2606 OID 21587)
-- Name: application_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY application
    ADD CONSTRAINT application_pk PRIMARY KEY (id);


--
-- TOC entry 2043 (class 2606 OID 21639)
-- Name: category_license_category_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY category_license
    ADD CONSTRAINT category_license_category_key UNIQUE (category);


--
-- TOC entry 2045 (class 2606 OID 21637)
-- Name: category_license_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY category_license
    ADD CONSTRAINT category_license_pk PRIMARY KEY (id);


--
-- TOC entry 2035 (class 2606 OID 21619)
-- Name: credentials_login_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY credentials
    ADD CONSTRAINT credentials_login_key UNIQUE (login);


--
-- TOC entry 2037 (class 2606 OID 21617)
-- Name: credentials_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY credentials
    ADD CONSTRAINT credentials_pk PRIMARY KEY (id);


--
-- TOC entry 2033 (class 2606 OID 21609)
-- Name: customer_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer
    ADD CONSTRAINT customer_pk PRIMARY KEY (id);


--
-- TOC entry 2031 (class 2606 OID 21604)
-- Name: driver_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY driver
    ADD CONSTRAINT driver_pk PRIMARY KEY (id);


--
-- TOC entry 2027 (class 2606 OID 21598)
-- Name: vehicle_license_plate_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY vehicle
    ADD CONSTRAINT vehicle_license_plate_key UNIQUE (license_plate);


--
-- TOC entry 2029 (class 2606 OID 21596)
-- Name: vehicle_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY vehicle
    ADD CONSTRAINT vehicle_pk PRIMARY KEY (id);


--
-- TOC entry 2039 (class 2606 OID 21627)
-- Name: vehicle_type_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY vehicle_type
    ADD CONSTRAINT vehicle_type_pk PRIMARY KEY (id);


--
-- TOC entry 2041 (class 2606 OID 21629)
-- Name: vehicle_type_type_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY vehicle_type
    ADD CONSTRAINT vehicle_type_type_key UNIQUE (type);


--
-- TOC entry 2046 (class 2606 OID 21643)
-- Name: application_fk0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY application
    ADD CONSTRAINT application_fk0 FOREIGN KEY (customer_id) REFERENCES customer(id);


--
-- TOC entry 2047 (class 2606 OID 21648)
-- Name: application_fk1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY application
    ADD CONSTRAINT application_fk1 FOREIGN KEY (vehicle_type_id) REFERENCES vehicle_type(id);


--
-- TOC entry 2048 (class 2606 OID 21653)
-- Name: application_fk2; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY application
    ADD CONSTRAINT application_fk2 FOREIGN KEY (driver_id) REFERENCES driver(id);


--
-- TOC entry 2052 (class 2606 OID 21673)
-- Name: customer_fk0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer
    ADD CONSTRAINT customer_fk0 FOREIGN KEY (id) REFERENCES credentials(id);


--
-- TOC entry 2053 (class 2606 OID 21678)
-- Name: driver_2_category_license_fk0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY driver_2_category_license
    ADD CONSTRAINT driver_2_category_license_fk0 FOREIGN KEY (driver_id) REFERENCES driver(id);


--
-- TOC entry 2054 (class 2606 OID 21683)
-- Name: driver_2_category_license_fk1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY driver_2_category_license
    ADD CONSTRAINT driver_2_category_license_fk1 FOREIGN KEY (category_license_id) REFERENCES category_license(id);


--
-- TOC entry 2051 (class 2606 OID 21668)
-- Name: driver_fk0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY driver
    ADD CONSTRAINT driver_fk0 FOREIGN KEY (id) REFERENCES credentials(id);


--
-- TOC entry 2049 (class 2606 OID 21658)
-- Name: vehicle_fk0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY vehicle
    ADD CONSTRAINT vehicle_fk0 FOREIGN KEY (id) REFERENCES driver(id);


--
-- TOC entry 2050 (class 2606 OID 21663)
-- Name: vehicle_fk1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY vehicle
    ADD CONSTRAINT vehicle_fk1 FOREIGN KEY (vehicle_type_id) REFERENCES vehicle_type(id);


-- Completed on 2016-06-10 09:20:20

--
-- PostgreSQL database dump complete
--

