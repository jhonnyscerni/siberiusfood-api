package br.com.siberius.siberiusfood.core.springfox;

import br.com.siberius.siberiusfood.api.exceptionhandler.Problem;
import br.com.siberius.siberiusfood.api.model.CidadeDTO;
import br.com.siberius.siberiusfood.api.model.CozinhaDTO;
import br.com.siberius.siberiusfood.api.model.EstadoDTO;
import br.com.siberius.siberiusfood.api.model.FormaPagamentoDTO;
import br.com.siberius.siberiusfood.api.model.GrupoDTO;
import br.com.siberius.siberiusfood.api.model.PedidoResumoDTO;
import br.com.siberius.siberiusfood.api.model.PermissaoDTO;
import br.com.siberius.siberiusfood.api.model.ProdutoDTO;
import br.com.siberius.siberiusfood.api.model.RestauranteBasicoDTO;
import br.com.siberius.siberiusfood.api.model.UsuarioDTO;
import br.com.siberius.siberiusfood.api.openapi.model.CidadeCollectionModelOpenApi;
import br.com.siberius.siberiusfood.api.openapi.model.CozinhasModelOpenApi;
import br.com.siberius.siberiusfood.api.openapi.model.EstadosModelOpenApi;
import br.com.siberius.siberiusfood.api.openapi.model.FormasPagamentoModelOpenApi;
import br.com.siberius.siberiusfood.api.openapi.model.GruposModelOpenApi;
import br.com.siberius.siberiusfood.api.openapi.model.LinksModelOpenApi;
import br.com.siberius.siberiusfood.api.openapi.model.PageableModelOpenApi;
import br.com.siberius.siberiusfood.api.openapi.model.PedidosResumoModelOpenApi;
import br.com.siberius.siberiusfood.api.openapi.model.PermissoesModelOpenApi;
import br.com.siberius.siberiusfood.api.openapi.model.ProdutosModelOpenApi;
import br.com.siberius.siberiusfood.api.openapi.model.RestaurantesBasicoModelOpenApi;
import br.com.siberius.siberiusfood.api.openapi.model.UsuariosModelOpenApi;
import com.fasterxml.classmate.TypeResolver;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.Arrays;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig implements WebMvcConfigurer {

    @Bean
    public Docket apiDocket() {

        TypeResolver typeResolver = new TypeResolver();

        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("br.com.siberius.siberiusfood.api"))
            .paths(PathSelectors.any())
            .build()
            .useDefaultResponseMessages(false)
            .globalResponseMessage(RequestMethod.GET, globalGetResponseMessages())
            .globalResponseMessage(RequestMethod.POST, globalPostPutResponseMessages())
            .globalResponseMessage(RequestMethod.PUT, globalPostPutResponseMessages())
            .globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessages())
//  Configurando de forma global o parametro "campos" do squiggly
//            .globalOperationParameters(Arrays.asList(
//                new ParameterBuilder()
//                    .name("campos")
//                    .description("Nomes das propriedades para filtrar na resposta, separados por vírgula")
//                    .parameterType("query")
//                    .modelRef(new ModelRef("string"))
//                    .build()
//            ))
            .additionalModels(typeResolver.resolve(Problem.class))
            .ignoredParameterTypes(ServletWebRequest.class,
                URL.class, URI.class, URLStreamHandler.class, Resource.class,
                File.class, InputStream.class)
            .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
            .directModelSubstitute(Links.class, LinksModelOpenApi.class)
            .alternateTypeRules(AlternateTypeRules.newRule(
                typeResolver.resolve(PagedModel.class, CozinhaDTO.class),
                CozinhasModelOpenApi.class))
            .alternateTypeRules(AlternateTypeRules.newRule(
                typeResolver.resolve(Page.class, PedidoResumoDTO.class),
                PedidosResumoModelOpenApi.class))
            .alternateTypeRules(AlternateTypeRules.newRule(
                typeResolver.resolve(CollectionModel.class, CidadeDTO.class),
                CidadeCollectionModelOpenApi.class))
            .alternateTypeRules(AlternateTypeRules.newRule(
                typeResolver.resolve(CollectionModel.class, EstadoDTO.class),
                EstadosModelOpenApi.class))
            .alternateTypeRules(AlternateTypeRules.newRule(
                typeResolver.resolve(CollectionModel.class, FormaPagamentoDTO.class),
                FormasPagamentoModelOpenApi.class))
            .alternateTypeRules(AlternateTypeRules.newRule(
                typeResolver.resolve(CollectionModel.class, GrupoDTO.class),
                GruposModelOpenApi.class))
            .alternateTypeRules(AlternateTypeRules.newRule(
                typeResolver.resolve(CollectionModel.class, PermissaoDTO.class),
                PermissoesModelOpenApi.class))
            .alternateTypeRules(AlternateTypeRules.newRule(
                typeResolver.resolve(PagedModel.class, PedidoResumoDTO.class),
                PedidosResumoModelOpenApi.class))
            .alternateTypeRules(AlternateTypeRules.newRule(
                typeResolver.resolve(CollectionModel.class, ProdutoDTO.class),
                ProdutosModelOpenApi.class))
            .alternateTypeRules(AlternateTypeRules.newRule(
                typeResolver.resolve(CollectionModel.class, RestauranteBasicoDTO.class),
                RestaurantesBasicoModelOpenApi.class))
            .alternateTypeRules(AlternateTypeRules.newRule(
                typeResolver.resolve(CollectionModel.class, UsuarioDTO.class),
                UsuariosModelOpenApi.class))
            .apiInfo(apiInfo())
            .tags(new Tag("Cidades", "Gerencia as cidades"),
                new Tag("Grupos", "Gerencia os grupos de usuários"),
                new Tag("Cozinhas", "Gerencia as cozinhas"),
                new Tag("Formas de pagamento", "Gerencia as formas de pagamento"),
                new Tag("Pedidos", "Gerencia os pedidos"),
                new Tag("Restaurantes", "Gerencia os restaurantes"),
                new Tag("Estados", "Gerencia os estados"),
                new Tag("Produtos", "Gerencia os produtos de restaurantes"),
                new Tag("Usuários", "Gerencia os usuários"),
                new Tag("Estatísticas", "Estatísticas da SiberiusFood"),
                new Tag("Permissões", "Gerencia as permissões"));
    }

    private List<ResponseMessage> globalGetResponseMessages() {
        return Arrays.asList(
            new ResponseMessageBuilder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Erro interno do servidor")
                .responseModel(new ModelRef("Problema"))
                .build(),
            new ResponseMessageBuilder()
                .code(HttpStatus.NOT_ACCEPTABLE.value())
                .message("Recurso não possui representação que poderia ser aceita pelo consumidor")
                .build()
        );
    }

    private List<ResponseMessage> globalPostPutResponseMessages() {
        return Arrays.asList(
            new ResponseMessageBuilder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message("Requisição inválida (erro do cliente)")
                .responseModel(new ModelRef("Problema"))
                .build(),
            new ResponseMessageBuilder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Erro interno no servidor")
                .responseModel(new ModelRef("Problema"))
                .build(),
            new ResponseMessageBuilder()
                .code(HttpStatus.NOT_ACCEPTABLE.value())
                .message("Recurso não possui representação que poderia ser aceita pelo consumidor")
                .build(),
            new ResponseMessageBuilder()
                .code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                .message("Requisição recusada porque o corpo está em um formato não suportado")
                .responseModel(new ModelRef("Problema"))
                .build()
        );
    }

    private List<ResponseMessage> globalDeleteResponseMessages() {
        return Arrays.asList(
            new ResponseMessageBuilder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message("Requisição inválida (erro do cliente)")
                .responseModel(new ModelRef("Problema"))
                .build(),
            new ResponseMessageBuilder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Erro interno no servidor")
                .responseModel(new ModelRef("Problema"))
                .build()
        );
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("SiberiusFood API")
            .description("API aberta para clientes e restaurantes")
            .version("1")
            .contact(new Contact("Siberius", "https://www.siberius.com.br", "contato@siberius.com.br"))
            .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
            .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}